package shopping_app.service;

import shopping_app.common.ApiResponse;
import shopping_app.dto.product.request.ProductFilter;
import shopping_app.dto.product.request.ProductRequest;
import shopping_app.dto.product.response.ProductResponse;

import java.util.List;

public interface IProductService {

    ApiResponse<List<ProductResponse>> getProducts(ProductFilter filter);

    ApiResponse<ProductResponse> getProductById(Long id);

    ApiResponse<String> createProduct(ProductRequest request);

    ApiResponse<String> updateProduct(ProductRequest request, Long id);

    ApiResponse<String> deleteProduct(Long id);
}
