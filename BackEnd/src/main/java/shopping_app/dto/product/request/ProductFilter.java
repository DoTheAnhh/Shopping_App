package shopping_app.dto.product.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shopping_app.entity.enums.ProductStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductFilter {

    private String code;

    private String name;

    private List<ProductStatus> statuses;

    private List<Long> brandIds;

    private Double priceFrom;

    private Double priceTo;

    private LocalDateTime createdAtFrom;

    private LocalDateTime createdAtTo;

    private String brandName;
}
