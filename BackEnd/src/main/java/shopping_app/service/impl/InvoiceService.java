package shopping_app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping_app.common.ApiResponse;
import shopping_app.dto.invoice.response.InvoiceResponse;
import shopping_app.entity.*;
import shopping_app.entity.enums.InvoiceStatus;
import shopping_app.entity.enums.PromotionType;
import shopping_app.mapper.InvoiceMapper;
import shopping_app.repository.InvoiceRepository;
import shopping_app.repository.PromotionProductVariantRepository;
import shopping_app.repository.PromotionRepository;
import shopping_app.service.IInvoiceService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InvoiceService implements IInvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final PromotionRepository promotionRepository;
    private final PromotionProductVariantRepository promotionProductVariantRepository;
    private final UserService userService;
    private final InvoiceMapper invoiceMapper;

    // Lấy giỏ hàng hiện tại (PENDING), nếu chưa có thì tạo mới
    @Override
    public Invoice getOrCreateCart(User user) {
        return invoiceRepository
                .findByUserAndStatus(user, InvoiceStatus.CART)
                .orElseGet(() -> {
                    Invoice invoice = new Invoice();
                    invoice.setUser(user);
                    invoice.setStatus(InvoiceStatus.CART);
                    invoice.setTotalAmount(0L);
                    return invoiceRepository.save(invoice);
                });
    }

    // Xác nhận đơn hàng đổi trạng thái thành đang chờ đợi admin duyệt
    @Override
    @Transactional
    public ApiResponse<InvoiceResponse> submitInvoice(User user, Long promotionId) {

        // Lấy giỏ hàng
        Invoice cart = invoiceRepository
                .findByUserAndStatus(user, InvoiceStatus.CART)
                .orElseThrow(() -> new RuntimeException("Giỏ hàng trống"));

        if (cart.getInvoiceDetails().isEmpty()) {
            throw new RuntimeException("Không thể thanh toán giỏ hàng trống");
        }

        // Trừ tồn kho
        for (InvoiceDetail detail : cart.getInvoiceDetails()) {
            ProductVariant variant = detail.getProductVariant();

            if (variant.getStock() < detail.getQuantity()) {
                throw new RuntimeException("Không đủ tồn kho cho sản phẩm: " + variant.getId());
            }

            variant.setStock(
                    variant.getStock() - detail.getQuantity()
            );
        }

        long discountTotal = 0L;
        Promotion promotion = null;

        // Xử lý khuyến mãi (nếu có)
        if (promotionId != null) {

            promotion = promotionRepository.findById(promotionId)
                    .orElseThrow(() -> new RuntimeException("Khuyến mãi không tồn tại"));

            // Validate chung cho mọi loại promotion
            validatePromotion(promotion, cart);

            if (promotion.getPromotionType() == PromotionType.INVOICE) {

                // Giảm trên tổng hoá đơn
                discountTotal = calculateDiscount(
                        promotion,
                        cart.getTotalAmount()
                );

            } else if (promotion.getPromotionType() == PromotionType.PRODUCT_VARIANT) {

                boolean hasApplicableProduct = false;

                // Giảm theo từng sản phẩm áp dụng
                for (InvoiceDetail detail : cart.getInvoiceDetails()) {

                    boolean applicable =
                            promotionProductVariantRepository
                                    .existsByPromotionAndProductVariant(
                                            promotion,
                                            detail.getProductVariant()
                                    );

                    if (!applicable) continue;

                    hasApplicableProduct = true;

                    long lineAmount =
                            detail.getPrice() * detail.getQuantity();

                    discountTotal += calculateDiscount(
                            promotion,
                            lineAmount
                    );
                }

                if (!hasApplicableProduct) {
                    throw new RuntimeException("Khuyến mãi không áp dụng cho sản phẩm nào trong giỏ");
                }
            }

            // Gán khuyến mãi vào invoice
            cart.setPromotion(promotion);

            // Tăng số lượt dùng (NHỚ save)
            promotion.setUsedCount(promotion.getUsedCount() + 1);
            promotionRepository.save(promotion);
        }

        // Trừ tiền (Sau khi giảm giá)
        long finalAmount = cart.getTotalAmount() - discountTotal;
        cart.setTotalAmount(Math.max(finalAmount, 0));

        // Check out
        cart.setStatus(InvoiceStatus.PENDING);
        invoiceRepository.save(cart);

        return ApiResponse.success(
                "Gửi đơn hàng thành công, chờ người bán xác nhận",
                invoiceMapper.toResponse(cart)
        );
    }

    // Validate điều kiện khuyến mãi
    private void validatePromotion(Promotion promotion, Invoice cart) {

        if (!Boolean.TRUE.equals(promotion.getIsActive())) {
            throw new RuntimeException("Khuyến mãi không còn hiệu lực");
        }

        if (promotion.getMaxUsage() != null &&
                promotion.getUsedCount() >= promotion.getMaxUsage()) {
            throw new RuntimeException("Khuyến mãi đã hết lượt sử dụng");
        }

        // Áp dụng cho cả khuyến mãi của Hóa đơn & Sản phẩm
        if (promotion.getMinOrderValue() != null &&
                cart.getTotalAmount() < promotion.getMinOrderValue()) {
            throw new RuntimeException("Chưa đủ điều kiện áp khuyến mãi");
        }

        LocalDateTime now = LocalDateTime.now();

        if (promotion.getStartAt() != null && now.isBefore(promotion.getStartAt())) {
            throw new RuntimeException("Khuyến mãi chưa bắt đầu");
        }

        if (promotion.getEndAt() != null && now.isAfter(promotion.getEndAt())) {
            throw new RuntimeException("Khuyến mãi đã kết thúc");
        }
    }

    // Tính số tiền được giảm
    private long calculateDiscount(Promotion promotion, long amount) {
        return switch (promotion.getDiscountType()) {
            case AMOUNT -> promotion.getDiscountValue();
            case PERCENT -> amount * promotion.getDiscountValue() / 100;
        };
    }

    @Transactional
    public ApiResponse<InvoiceResponse> changeInvoiceStatus(Long invoiceId, InvoiceStatus nextStatus) {

        // Lấy user hiện tại
        User currentUser = userService.getCurrentUserEntity();

        // Lấy hoá đơn
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Hoá đơn không tồn tại"));

        InvoiceStatus currentStatus = invoice.getStatus();

        // Check logic trạng thái
        if (!currentStatus.canChangeTo(nextStatus)) {
            throw new RuntimeException(
                    "Không thể chuyển trạng thái từ "
                            + currentStatus + " sang " + nextStatus
            );
        }

        // Check quyền theo ROLE
        checkPermissionByRole(currentUser, currentStatus, nextStatus);

        // Xử lý nghiệp vụ phụ theo trạng thái
        handleBusinessWhenChangeStatus(invoice, nextStatus);

        // Đổi trạng thái
        invoice.setStatus(nextStatus);
        invoiceRepository.save(invoice);

        return ApiResponse.success(
                "Cập nhật trạng thái hoá đơn thành công",
                invoiceMapper.toResponse(invoice)
        );
    }

    private void checkPermissionByRole(User user, InvoiceStatus current, InvoiceStatus next) {

        boolean isAdmin = user.getRoles().stream()
                .anyMatch(r -> r.getName().equals("ADMIN"));

        boolean isShipper = user.getRoles().stream()
                .anyMatch(r -> r.getName().equals("SHIPPER"));

        // ADMIN
        if (isAdmin) {
            if (current == InvoiceStatus.PENDING &&
                    (next == InvoiceStatus.CONFIRMED || next == InvoiceStatus.CANCELLED)) {
                return;
            }
            throw new RuntimeException("ADMIN không được phép chuyển trạng thái này");
        }

        // SHIPPER
        if (isShipper) {
            if ((current == InvoiceStatus.CONFIRMED && next == InvoiceStatus.SHIPPED)
                    || (current == InvoiceStatus.SHIPPED && next == InvoiceStatus.COMPLETED)) {
                return;
            }
            throw new RuntimeException("SHIPPER không được phép chuyển trạng thái này");
        }

        // USER
        throw new RuntimeException("Bạn không có quyền thay đổi trạng thái hoá đơn");
    }

    private void handleBusinessWhenChangeStatus(Invoice invoice, InvoiceStatus nextStatus) {

        // Huỷ đơn → hoàn kho + hoàn promotion
        if (nextStatus == InvoiceStatus.CANCELLED) {

            for (InvoiceDetail detail : invoice.getInvoiceDetails()) {
                ProductVariant variant = detail.getProductVariant();
                variant.setStock(
                        variant.getStock() + detail.getQuantity()
                );
            }

            if (invoice.getPromotion() != null) {
                Promotion promotion = invoice.getPromotion();
                promotion.setUsedCount(
                        promotion.getUsedCount() - 1
                );
                promotionRepository.save(promotion);
            }
        }

        // SHIPPED / COMPLETED
    }
}

