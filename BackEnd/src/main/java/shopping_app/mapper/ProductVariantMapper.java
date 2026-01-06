package shopping_app.mapper;

import org.springframework.stereotype.Component;
import shopping_app.dto.product_image.response.ProductImageResponse;
import shopping_app.dto.product_variant.response.ProductVariantResponse;
import shopping_app.entity.ProductImage;
import shopping_app.entity.ProductVariant;

import java.text.DecimalFormat;
import java.util.List;

@Component
public class ProductVariantMapper {

    public ProductVariantResponse toResponse(ProductVariant variant) {
        if (variant == null) return null;

        ProductVariantResponse res = new ProductVariantResponse();
        res.setId(variant.getId());
        res.setProductName(variant.getProduct() != null ? variant.getProduct().getName() : null);
        res.setSizeName(variant.getSize() != null ? variant.getSize().getName() : null);
        res.setColorName(variant.getColor() != null ? variant.getColor().getName() : null);
        res.setPrice(String.valueOf(variant.getPrice()));
        res.setStock(String.valueOf(variant.getStock()));
        if (variant.getProduct() != null) {
            res.setProductImages(mapImages(variant.getProduct().getImages()));
        }

        return res;
    }

    public List<ProductVariantResponse> toResponses(List<ProductVariant> variants) {
        if (variants == null || variants.isEmpty()) {
            return List.of();
        }
        return variants.stream()
                .map(this::toResponse)
                .toList();
    }

    private List<ProductImageResponse> mapImages(List<ProductImage> images) {
        if (images == null || images.isEmpty()) {
            return List.of();
        }

        return images.stream()
                .map(this::mapImage)
                .toList();
    }

    private ProductImageResponse mapImage(ProductImage image) {
        if (image == null) return null;

        ProductImageResponse res = new ProductImageResponse();
        res.setUrl(image.getUrl());
        res.setIsPrimary(image.getIsPrimary());
        return res;
    }
}