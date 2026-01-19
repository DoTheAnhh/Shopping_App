package shopping_app.controler;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping_app.common.ApiResponse;
import shopping_app.dto.invoiceDetail.response.InvoiceDetailResponse;
import shopping_app.service.IInvoiceDetailService;
import shopping_app.service.impl.InvoiceDetailService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("cart-detail")
@Tag(name = "Cart detail")
public class CartDetailController {

    private final InvoiceDetailService invoiceDetailService;

    @GetMapping("{invoiceId}")
    public ApiResponse<List<InvoiceDetailResponse>> getCartDetailByInvoiceId(@PathVariable Long invoiceId) {
        return invoiceDetailService.getInvoiceDetailByInvoiceId(invoiceId);
    }
}
