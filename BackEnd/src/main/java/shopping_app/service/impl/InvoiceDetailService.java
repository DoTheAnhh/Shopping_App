package shopping_app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping_app.common.ApiResponse;
import shopping_app.dto.cart.request.AddProductVariantToCartRequest;
import shopping_app.dto.invoiceDetail.response.InvoiceDetailResponse;
import shopping_app.entity.Invoice;
import shopping_app.entity.InvoiceDetail;
import shopping_app.entity.ProductVariant;
import shopping_app.entity.User;
import shopping_app.mapper.InvoiceDetailMapper;
import shopping_app.repository.InvoiceDetailRepository;
import shopping_app.repository.ProductVariantRepository;
import shopping_app.service.IInvoiceDetailService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceDetailService implements IInvoiceDetailService {

    private final InvoiceDetailRepository invoiceDetailRepository;
    private final InvoiceService invoiceService;
    private final ProductVariantRepository productVariantRepository;
    private final InvoiceDetailMapper mapper;

    @Override
    public ApiResponse<List<InvoiceDetailResponse>> getInvoiceDetailByInvoiceId(Long invoiceId) {
        List<InvoiceDetail> invoiceDetails = invoiceDetailRepository.findByInvoiceId(invoiceId);

        if (invoiceDetails.isEmpty()) {
            throw new RuntimeException("Hoá đơn chi tiết không tồn tại với hoá đơn có id: " + invoiceId);
        }

        List<InvoiceDetailResponse> responses = mapper.toResponses(invoiceDetails);
        return ApiResponse.success(responses);
    }

    @Override
    @Transactional
    public void addProductVariantToCart(User user, AddProductVariantToCartRequest req) {

        Invoice cart = invoiceService.getOrCreateCart(user);

        ProductVariant variant = productVariantRepository.findById(req.getProductVariantId())
                .orElseThrow(() -> new RuntimeException("Sản phẩm chi tiết không tồn tại"));

        InvoiceDetail invoiceDetail = invoiceDetailRepository
                .findByInvoiceIdAndProductVariantId(cart.getId(), variant.getId())
                .orElse(null);

        if (invoiceDetail == null) {
            invoiceDetail = new InvoiceDetail();
            invoiceDetail.setInvoice(cart);
            invoiceDetail.setProductVariant(variant);
            invoiceDetail.setPrice(variant.getPrice());
            invoiceDetail.setQuantity(req.getQuantity());
        } else {
            invoiceDetail.setQuantity(invoiceDetail.getQuantity() + req.getQuantity());
        }

        invoiceDetail.setTotal(invoiceDetail.getPrice() * invoiceDetail.getQuantity());
        invoiceDetailRepository.save(invoiceDetail);

        // Cập nhật tổng tiền vào db
        cart.setTotalAmount(
                cart.getTotalAmount() + (variant.getPrice() * req.getQuantity())
        );
    }
}
