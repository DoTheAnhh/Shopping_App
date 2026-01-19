package shopping_app.controler;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shopping_app.common.ApiResponse;
import shopping_app.dto.invoice.response.InvoiceResponse;
import shopping_app.entity.User;
import shopping_app.entity.enums.InvoiceStatus;
import shopping_app.service.IInvoiceService;
import shopping_app.service.IUserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("invoice")
@Tag(name = "Invoice")
public class InvoiceController {

    private final IInvoiceService invoiceService;
    private final IUserService userService;

    // invoice/10/status?status=CONFIRMED
    @PostMapping("/{invoiceId}/status")
    public ApiResponse<InvoiceResponse> changeStatus(@PathVariable Long invoiceId, @RequestParam InvoiceStatus status) {
        return invoiceService.changeInvoiceStatus(invoiceId, status);
    }

    // Xác nhận đơn hàng (user xác nhận -> pending)
    @PostMapping("/submit-invoice")
    public ApiResponse<InvoiceResponse> submitInvoice(@RequestParam(required = false) Long promotionId) {
        User currentUser = userService.getCurrentUserEntity();
        return invoiceService.submitInvoice(currentUser, promotionId);
    }
}
