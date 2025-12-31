package shopping_app.mapper;

import shopping_app.dto.product.response.ProductResponse;
import shopping_app.dto.product_image.response.ProductImageResponse;
import shopping_app.entity.Product;
import shopping_app.entity.ProductImage;

import java.util.List;

public class ProductMapper {

    public static ProductResponse toResponse(Product product) {
        if (product == null) return null;

        ProductResponse res = new ProductResponse();
        res.setId(product.getId());
        res.setCode(product.getCode());
        res.setName(product.getName());
        res.setDescription(product.getDescription());
        res.setStatus(product.getStatus().getLabel());

        // brand
        res.setBrandName(
                product.getBrand() != null
                        ? product.getBrand().getName()
                        : null
        );

        // images
        res.setImages(mapImages(product.getImages()));

        return res;
    }

    public static List<ProductResponse> toResponses(List<Product> products) {
        if (products == null || products.isEmpty()) {
            return List.of();
        }

        return products.stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    private static List<ProductImageResponse> mapImages(List<ProductImage> images) {
        if (images == null || images.isEmpty()) {
            return List.of();
        }

        return images.stream()
                .map(ProductMapper::mapImage)
                .toList();
    }

    private static ProductImageResponse mapImage(ProductImage image) {
        if (image == null) return null;

        ProductImageResponse res = new ProductImageResponse();
        res.setId(image.getId());
        res.setUrl(image.getUrl());
        res.setIsPrimary(image.getIsPrimary());
        return res;
    }
}
