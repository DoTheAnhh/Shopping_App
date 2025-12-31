package shopping_app.mapper;

import shopping_app.dto.product_image.response.ProductImageResponse;
import shopping_app.dto.product_variant.response.ProductVariantResponse;
import shopping_app.entity.ProductImage;
import shopping_app.entity.ProductVariant;

import java.text.DecimalFormat;
import java.util.List;

public class ProductVariantMapper {

    public static ProductVariantResponse toResponse(ProductVariant variant) {
        if (variant == null) return null;

        ProductVariantResponse res = new ProductVariantResponse();
        res.setId(variant.getId());
        res.setProductName(variant.getProduct() != null ? variant.getProduct().getName() : null);
        res.setSizeName(variant.getSize() != null ? variant.getSize().getName() : null);
        res.setColorName(variant.getColor() != null ? variant.getColor().getName() : null);
        if (variant.getPrice() != null) {
            DecimalFormat df = new DecimalFormat("#,###");
            String formatted = df.format(variant.getPrice()).replace(",", "."); // đổi thành dấu chấm
            res.setPrice(formatted);
        }
        res.setStock(String.valueOf(variant.getStock()));
        if (variant.getProduct() != null && variant.getProduct().getImages() != null) {
            List<ProductImageResponse> images = variant.getProduct().getImages().stream()
                    .map(image -> {
                        ProductImageResponse imgRes = new ProductImageResponse();
                        imgRes.setUrl(image.getUrl());
                        imgRes.setIsPrimary(image.getIsPrimary());
                        return imgRes;
                    })
                    .toList();
            res.setProductImages(images);
        }

        return res;
    }

    public static List<ProductVariantResponse> toResponses(List<ProductVariant> variants) {
        if (variants == null || variants.isEmpty()) return List.of();
        return variants.stream().map(ProductVariantMapper::toResponse).toList();
    }
}
