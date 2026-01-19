package shopping_app.dto.invoiceDetail.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDetailResponse {

    private Long id;

    private Long productVariantId;

    private String productName;

    private String productImage;

    private String sizeName;

    private String colorName;

    private String brandName;

    private String price;

    private Integer quantity;

    private String total;
}
