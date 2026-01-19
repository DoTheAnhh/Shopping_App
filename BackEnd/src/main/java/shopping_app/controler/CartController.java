package shopping_app.controler;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping_app.common.ApiResponse;
import shopping_app.dto.cart.request.AddProductVariantToCartRequest;
import shopping_app.entity.User;
import shopping_app.service.IInvoiceDetailService;
import shopping_app.service.IUserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("cart")
@Tag(name = "Cart")
public class CartController {

    private final IInvoiceDetailService invoiceDetailService;
    private final IUserService userService;

    @PostMapping("/add-product-variant-to-cart")
    public ApiResponse<String> addProductVariantToCart(
            @RequestBody AddProductVariantToCartRequest request
    ) {
        User user = userService.getCurrentUserEntity();
        invoiceDetailService.addProductVariantToCart(user, request);
        return ApiResponse.success("Thêm sản phẩm vào giỏ hàng thành công");
    }
}
