package shopping_app.dto.invoiceDetail.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDetailRequest {

    private Long productVariantId;

    private Integer quantity;
}
