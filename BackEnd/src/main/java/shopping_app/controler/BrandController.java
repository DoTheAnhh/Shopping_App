package shopping_app.controler;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import shopping_app.common.ApiResponse;
import shopping_app.dto.brand.request.BrandFilter;
import shopping_app.dto.brand.request.BrandRequest;
import shopping_app.dto.brand.response.BrandResponse;
import shopping_app.service.IBrandService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("brand")
@Tag(name = "Brand") //Rename cho Swagger
public class BrandController {

    private final IBrandService brandService;

    @PostMapping("list")
    public ApiResponse<List<BrandResponse>> getBrands(@RequestBody BrandFilter filter) {
        return brandService.getBrands(filter);
    }

    @GetMapping("{id}")
    public ApiResponse<BrandResponse> getBrandById(@PathVariable("id") Long id) {
        return brandService.getBrandById(id);
    }

    @PostMapping("create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<String> createBrand(@RequestBody BrandRequest request) {
        return brandService.createBrand(request);
    }

    @PatchMapping("update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<String> updateBrand(@RequestBody BrandRequest request, @PathVariable("id") Long id) {
        return brandService.updateBrand(request, id);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<String> deleteBrand(@PathVariable("id") Long id) {
        return brandService.deleteBrand(id);
    }
}
