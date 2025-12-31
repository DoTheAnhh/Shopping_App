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

    private String productCode;

    private String productName;

    private String sizeCode;

    private String sizeName;

    private String colorCode;

    private String colorName;

    private Long priceFrom;

    private Long priceTo;

    private boolean isHasStock;

    private LocalDateTime createdAtFrom;

    private LocalDateTime createdAtTo;
}
