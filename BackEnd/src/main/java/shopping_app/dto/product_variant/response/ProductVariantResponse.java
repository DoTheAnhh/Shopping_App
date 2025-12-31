package shopping_app.dto.product_variant.response;

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
public class ProductVariantResponse {

    private Long id;

    private String productName;

    private List<ProductImageResponse> productImages;

    private String sizeName;

    private String colorName;

    private String price;

    private String stock;
}
