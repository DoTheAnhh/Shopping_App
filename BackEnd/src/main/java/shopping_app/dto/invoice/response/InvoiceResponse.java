package shopping_app.dto.invoice.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shopping_app.dto.invoiceDetail.response.InvoiceDetailResponse;
import shopping_app.entity.enums.InvoiceStatus;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceResponse {

    private Long id;

    private String totalAmount;

    private InvoiceStatus status;

    private Long promotionId;

    private String promotionCode;

    private String promotionName;

    private List<InvoiceDetailResponse> invoiceDetails;
}
