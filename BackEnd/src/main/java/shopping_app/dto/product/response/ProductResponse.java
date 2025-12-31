package shopping_app.dto.product.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shopping_app.dto.product_image.response.ProductImageResponse;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private Long id;

    private String code;

    private String name;

    private String description;

    private String status;

    private String brandName;

    private List<ProductImageResponse> images;
}
