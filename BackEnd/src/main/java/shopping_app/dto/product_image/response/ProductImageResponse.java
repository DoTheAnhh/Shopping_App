package shopping_app.dto.product_image.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageResponse {

    private Long id;

    private String url;

    private Boolean isPrimary;
}
