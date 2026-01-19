package shopping_app.service;

import shopping_app.common.ApiResponse;
import shopping_app.dto.invoice.response.InvoiceResponse;
import shopping_app.entity.Invoice;
import shopping_app.entity.User;
import shopping_app.entity.enums.InvoiceStatus;

public interface IInvoiceService {

    Invoice getOrCreateCart(User user);

    // Xác nhận đơn hàng (user xác nhận -> pending)
    ApiResponse<InvoiceResponse> submitInvoice(User user, Long promotionId);

    // Đổi trạng thái đơn hàng (hóa đơn)
    ApiResponse<InvoiceResponse> changeInvoiceStatus(Long invoiceId, InvoiceStatus nextStatus);
}
