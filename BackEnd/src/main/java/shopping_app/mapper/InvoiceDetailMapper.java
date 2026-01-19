package shopping_app.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shopping_app.dto.invoiceDetail.response.InvoiceDetailResponse;
import shopping_app.entity.InvoiceDetail;
import shopping_app.entity.ProductImage;
import shopping_app.entity.ProductVariant;
import shopping_app.util.MoneyUtil;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InvoiceDetailMapper {

    private final MoneyUtil moneyUtil;

    public InvoiceDetailResponse toResponse(InvoiceDetail detail) {
        if (detail == null) return null;

        InvoiceDetailResponse res = new InvoiceDetailResponse();
        res.setId(detail.getId());
        res.setQuantity(detail.getQuantity());
        res.setPrice(moneyUtil.format(detail.getPrice()));
        res.setTotal(moneyUtil.format(detail.getTotal()));

        ProductVariant variant = detail.getProductVariant();
        if (variant != null) {
            res.setProductVariantId(variant.getId());

            if (variant.getSize() != null) {
                res.setSizeName(variant.getSize().getName());
            }

            if (variant.getColor() != null) {
                res.setColorName(variant.getColor().getName());
            }

            if (variant.getProduct() != null) {
                res.setProductName(variant.getProduct().getName());

                if (variant.getProduct().getBrand() != null) {
                    res.setBrandName(variant.getProduct().getBrand().getName());
                }

                res.setProductImage(getPrimaryImage(variant.getProduct().getImages()));
            }
        }

        return res;
    }

    public List<InvoiceDetailResponse> toResponses(List<InvoiceDetail> details) {
        if (details == null || details.isEmpty()) {
            return List.of();
        }

        return details.stream()
                .map(this::toResponse)
                .toList();
    }

    private String getPrimaryImage(List<ProductImage> images) {
        if (images == null || images.isEmpty()) return null;

        return images.stream()
                .filter(img -> Boolean.TRUE.equals(img.getIsPrimary()))
                .map(ProductImage::getUrl)
                .findFirst()
                .orElse(images.get(0).getUrl());
    }
}
