package shopping_app.service;

import shopping_app.common.ApiResponse;
import shopping_app.dto.cart.request.AddProductVariantToCartRequest;
import shopping_app.dto.invoiceDetail.response.InvoiceDetailResponse;
import shopping_app.entity.User;

import java.util.List;

public interface IInvoiceDetailService {

    ApiResponse<List<InvoiceDetailResponse>> getInvoiceDetailByInvoiceId(Long invoiceId);

    void addProductVariantToCart(User user, AddProductVariantToCartRequest req);
}
