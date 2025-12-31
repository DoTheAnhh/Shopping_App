package shopping_app.dto.product.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shopping_app.dto.product_image.response.ProductImageRequest;
import shopping_app.entity.enums.ProductStatus;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    private String code;

    private String name;

    private String description;

    private ProductStatus status;

    private Long brandId;

    private List<ProductImageRequest> images;
}
