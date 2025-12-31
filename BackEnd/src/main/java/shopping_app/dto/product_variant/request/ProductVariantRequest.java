package shopping_app.dto.product_variant.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantRequest {

    private String productId;

    private String sizeId;

    private String colorId;

    private Long price;

    private Integer stock;
}
