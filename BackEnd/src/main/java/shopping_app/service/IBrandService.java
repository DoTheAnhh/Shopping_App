package shopping_app.service;

import shopping_app.common.ApiResponse;
import shopping_app.dto.brand.request.BrandFilter;
import shopping_app.dto.brand.request.BrandRequest;
import shopping_app.dto.brand.response.BrandResponse;

import java.util.List;

public interface IBrandService {

    ApiResponse<List<BrandResponse>> getBrands(BrandFilter filter);

    ApiResponse<BrandResponse> getBrandById(Long id);

    ApiResponse<String> createBrand(BrandRequest request);

    ApiResponse<String> updateBrand(BrandRequest request, Long id);

    ApiResponse<String> deleteBrand(Long id);
}
