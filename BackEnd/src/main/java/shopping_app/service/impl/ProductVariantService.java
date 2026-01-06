package shopping_app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import shopping_app.common.ApiResponse;
import shopping_app.dto.product_variant.request.ProductVariantFilter;
import shopping_app.dto.product_variant.request.ProductVariantRequest;
import shopping_app.dto.product_variant.response.ProductVariantResponse;
import shopping_app.entity.Color;
import shopping_app.entity.Product;
import shopping_app.entity.ProductVariant;
import shopping_app.entity.Size;
import shopping_app.exception.ValidationException;
import shopping_app.mapper.ProductVariantMapper;
import shopping_app.repository.ColorRepository;
import shopping_app.repository.ProductRepository;
import shopping_app.repository.ProductVariantRepository;
import shopping_app.repository.SizeRepository;
import shopping_app.service.IProductVariantService;
import shopping_app.specification.GenericSpecification;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductVariantService implements IProductVariantService {

    private final ProductVariantRepository variantRepository;
    private final ProductRepository productRepository;
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;
    private final GenericSpecification<ProductVariant> specification;

    @Override
    public ApiResponse<List<ProductVariantResponse>> getProductVariants(ProductVariantFilter filter) {
        Specification<ProductVariant> spec = specification.build(filter);
        List<ProductVariant> variants = variantRepository.findAll(spec);
        return ApiResponse.success(ProductVariantMapper.toResponses(variants));
    }

    @Override
    public ApiResponse<ProductVariantResponse> getProductVariantById(Long id) {
        ProductVariant variant = variantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại với id: " + id));
        return ApiResponse.success(ProductVariantMapper.toResponse(variant));
    }

    @Override
    public ApiResponse<String> createProductVariant(ProductVariantRequest request) {
        try {
            validate(request);
            Product product = productRepository.findById(Long.parseLong(request.getProductId()))
                    .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));
            Size size = sizeRepository.findById(Long.parseLong(request.getSizeId()))
                    .orElseThrow(() -> new RuntimeException("Kích thước không tồn tại"));
            Color color = colorRepository.findById(Long.parseLong(request.getColorId()))
                    .orElseThrow(() -> new RuntimeException("Màu sắc không tồn tại"));

            ProductVariant variant = new ProductVariant();
            variant.setProduct(product);
            variant.setSize(size);
            variant.setColor(color);
            variant.setPrice(request.getPrice());
            variant.setStock(request.getStock());

            variantRepository.save(variant);
            return ApiResponse.success("Thành công", variant.getId().toString());
        } catch (Exception e) {
            return ApiResponse.error("Lỗi: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<String> updateProductVariant(ProductVariantRequest request, Long id) {
        try {
            validate(request);

            ProductVariant variant = variantRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Sản phẩm variant không tồn tại"));

            Product product = productRepository.findById(Long.parseLong(request.getProductId()))
                    .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));
            Size size = sizeRepository.findById(Long.parseLong(request.getSizeId()))
                    .orElseThrow(() -> new RuntimeException("Kích thước không tồn tại"));
            Color color = colorRepository.findById(Long.parseLong(request.getColorId()))
                    .orElseThrow(() -> new RuntimeException("Màu sắc không tồn tại"));

            variant.setProduct(product);
            variant.setSize(size);
            variant.setColor(color);
            variant.setPrice(request.getPrice());
            variant.setStock(request.getStock());

            variantRepository.save(variant);
            return ApiResponse.success("Cập nhật thành công", variant.getId().toString());
        } catch (Exception e) {
            return ApiResponse.error("Lỗi: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<String> deleteProductVariant(Long id) {
        try {
            ProductVariant variant = variantRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Sản phẩm variant không tồn tại"));
            variant.setDeletedAt(LocalDateTime.now());
            variantRepository.save(variant);
            return ApiResponse.success("Xóa thành công", variant.getId().toString());
        } catch (Exception e) {
            return ApiResponse.error("Lỗi: " + e.getMessage());
        }
    }

    private void validate(ProductVariantRequest request) {
        Map<String, String> errors = new HashMap<>();

        if (request.getProductId() == null || request.getProductId().isBlank()) {
            errors.put("productId", "Không được để trống");
        }
        if (request.getSizeId() == null || request.getSizeId().isBlank()) {
            errors.put("sizeId", "Không được để trống");
        }
        if (request.getColorId() == null || request.getColorId().isBlank()) {
            errors.put("colorId", "Không được để trống");
        }
        if (request.getPrice() != null && request.getPrice() < 0) {
            errors.put("price", "Giá phải >= 0");
        }
        if (request.getStock() != null && request.getStock() < 0) {
            errors.put("stock", "Số lượng tồn phải >= 0");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
