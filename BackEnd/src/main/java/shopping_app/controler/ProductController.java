package shopping_app.controler;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import shopping_app.common.ApiResponse;
import shopping_app.dto.product.request.ProductFilter;
import shopping_app.dto.product.request.ProductRequest;
import shopping_app.dto.product.response.ProductResponse;
import shopping_app.service.impl.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("product")
@Tag(name = "Product") //Rename cho Swagger
public class ProductController {

    private final ProductService productService;

    @PostMapping("list")
    public ApiResponse<List<ProductResponse>> getProducts(@RequestBody ProductFilter filter) {
        return productService.getProducts(filter);
    }

    @GetMapping("{id}")
    public ApiResponse<ProductResponse> getProductById(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }

    @PostMapping("create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<String> createProduct(@RequestBody ProductRequest request) {
        return productService.createProduct(request);
    }

    @PatchMapping("update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<String> updateProduct(@RequestBody ProductRequest request, @PathVariable("id") Long id) {
        return productService.updateProduct(request, id);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<String> deleteProduct(@PathVariable("id") Long id) {
        return productService.deleteProduct(id);
    }
}
