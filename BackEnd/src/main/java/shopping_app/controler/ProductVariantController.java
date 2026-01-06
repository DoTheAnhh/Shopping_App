package shopping_app.controler;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import shopping_app.common.ApiResponse;
import shopping_app.dto.product_variant.request.ProductVariantFilter;
import shopping_app.dto.product_variant.request.ProductVariantRequest;
import shopping_app.dto.product_variant.response.ProductVariantResponse;
import shopping_app.service.impl.ProductVariantService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("product-variant")
@Tag(name = "Product Variant") //Rename cho Swagger
public class ProductVariantController {

    private final ProductVariantService variantService;

    @PostMapping("list")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ApiResponse<List<ProductVariantResponse>> getProductVariants(@RequestBody ProductVariantFilter filter) {
        return variantService.getProductVariants(filter);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ApiResponse<ProductVariantResponse> getProductVariantById(@PathVariable("id") Long id) {
        return variantService.getProductVariantById(id);
    }

    @PostMapping("create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<String> createProductVariant(@RequestBody ProductVariantRequest request) {
        return variantService.createProductVariant(request);
    }

    @PatchMapping("update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<String> updateProductVariant(@RequestBody ProductVariantRequest request, @PathVariable("id") Long id) {
        return variantService.updateProductVariant(request, id);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<String> deleteProductVariant(@PathVariable("id") Long id) {
        return variantService.deleteProductVariant(id);
    }
}
