package shopping_app.dto.product_variant.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantFilter {

    private Long productId;

    private Long sizeId;

    private String colorId;

    private Long priceFrom;

    private Long priceTo;

    private Boolean isHasStock;

    private LocalDateTime createdAtFrom;

    private LocalDateTime createdAtTo;
}
