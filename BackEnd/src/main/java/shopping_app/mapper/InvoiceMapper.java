package shopping_app.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shopping_app.dto.invoice.response.InvoiceResponse;
import shopping_app.entity.Invoice;
import shopping_app.entity.InvoiceDetail;
import shopping_app.entity.Promotion;
import shopping_app.util.MoneyUtil;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InvoiceMapper {

    private final InvoiceDetailMapper invoiceDetailMapper;
    private final MoneyUtil moneyUtil;

    public InvoiceResponse toResponse(Invoice invoice) {
        if (invoice == null) return null;

        InvoiceResponse res = new InvoiceResponse();
        res.setId(invoice.getId());
        res.setStatus(invoice.getStatus());

        // Format tiền
        res.setTotalAmount(
                moneyUtil.format(invoice.getTotalAmount())
        );

        // Promotion (nếu có)
        Promotion promotion = invoice.getPromotion();
        if (promotion != null) {
            res.setPromotionId(promotion.getId());
            res.setPromotionCode(promotion.getCode());
            res.setPromotionName(promotion.getName());
        }

        // Invoice details
        List<InvoiceDetail> details = invoice.getInvoiceDetails();
        if (details != null && !details.isEmpty()) {
            res.setInvoiceDetails(
                    invoiceDetailMapper.toResponses(details)
            );
        }

        return res;
    }
}
