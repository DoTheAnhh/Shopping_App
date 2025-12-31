package shopping_app.service;

import shopping_app.common.ApiResponse;
import shopping_app.dto.product_variant.request.ProductVariantFilter;
import shopping_app.dto.product_variant.request.ProductVariantRequest;
import shopping_app.dto.product_variant.response.ProductVariantResponse;

import java.util.List;

public interface IProductVariantService {

    ApiResponse<List<ProductVariantResponse>> getProductVariants(ProductVariantFilter filter);

    ApiResponse<ProductVariantResponse> getProductVariantById(Long id);

    ApiResponse<String> createProductVariant(ProductVariantRequest request);

    ApiResponse<String> updateProductVariant(ProductVariantRequest request, Long id);

    ApiResponse<String> deleteProductVariant(Long id);
}
