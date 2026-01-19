package shopping_app.dto.cart.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddProductVariantToCartRequest {

    private Long productVariantId;

    private Integer quantity;
}
