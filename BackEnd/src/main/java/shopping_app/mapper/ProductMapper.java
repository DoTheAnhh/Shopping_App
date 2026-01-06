package shopping_app.mapper;

import org.springframework.stereotype.Component;
import shopping_app.dto.product.response.ProductResponse;
import shopping_app.dto.product_image.response.ProductImageResponse;
import shopping_app.entity.Product;
import shopping_app.entity.ProductImage;

import java.util.List;

@Component
public class ProductMapper {

    public ProductResponse toResponse(Product product) {
        if (product == null) return null;

        ProductResponse res = new ProductResponse();
        res.setId(product.getId());
        res.setCode(product.getCode());
        res.setName(product.getName());
        res.setDescription(product.getDescription());
        res.setStatus(product.getStatus().getLabel());

        res.setBrandName(
                product.getBrand() != null
                        ? product.getBrand().getName()
                        : null
        );

        res.setImages(mapImages(product.getImages()));
        return res;
    }

    public List<ProductResponse> toResponses(List<Product> products) {
        if (products == null || products.isEmpty()) {
            return List.of();
        }

        return products.stream()
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
        res.setId(image.getId());
        res.setUrl(image.getUrl());
        res.setIsPrimary(image.getIsPrimary());
        return res;
    }
}
