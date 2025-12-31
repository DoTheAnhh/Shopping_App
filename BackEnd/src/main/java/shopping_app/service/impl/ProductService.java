package shopping_app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import shopping_app.common.ApiResponse;
import shopping_app.dto.product.request.ProductFilter;
import shopping_app.dto.product.request.ProductRequest;
import shopping_app.dto.product.response.ProductResponse;
import shopping_app.dto.product_image.response.ProductImageRequest;
import shopping_app.entity.Brand;
import shopping_app.entity.Product;
import shopping_app.entity.ProductImage;
import shopping_app.exception.ValidationException;
import shopping_app.mapper.ProductMapper;
import shopping_app.repository.BrandRepository;
import shopping_app.repository.ProductRepository;
import shopping_app.service.IProductService;
import shopping_app.specification.GenericSpecification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final GenericSpecification<Product> specification;

    @Override
    public ApiResponse<List<ProductResponse>> getProducts(ProductFilter filter) {

        Specification<Product> spec = specification.build(filter);

        List<Product> products = productRepository.findAll(spec);

        List<ProductResponse> responses = ProductMapper.toResponses(products);

        return ApiResponse.success(responses);
    }

    @Override
    public ApiResponse<ProductResponse> getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại với id: " + id));

        ProductResponse response = ProductMapper.toResponse(product);
        return ApiResponse.success(response);
    }

    @Override
    public ApiResponse<String> createProduct(ProductRequest request) {
        validate(request);

        try {
            Brand brand = null;
            if (request.getBrandId() != null) {
                brand = brandRepository.findById(request.getBrandId()).orElse(null);
            }

            Product product = new Product();
            product.setCode(request.getCode());
            product.setName(request.getName());
            product.setDescription(request.getDescription());
            product.setStatus(request.getStatus());
            product.setBrand(brand);

            List<ProductImage> images = new ArrayList<>();
            if (request.getImages() != null) {
                for (ProductImageRequest imgReq : request.getImages()) {
                    ProductImage image = new ProductImage();
                    image.setUrl(imgReq.getUrl());
                    image.setIsPrimary(imgReq.getIsPrimary());
                    image.setProduct(product);
                    images.add(image);
                }
            }
            product.setImages(images);

            productRepository.save(product);

            return ApiResponse.success("Thành công", product.getId().toString());

        } catch (Exception e) {
            return ApiResponse.error("Lỗi: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<String> updateProduct(ProductRequest request, Long id) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

            validate(request);

            Brand brand = null;
            if (request.getBrandId() != null) {
                brand = brandRepository.findById(request.getBrandId()).orElse(null);
            }

            product.setCode(request.getCode());
            product.setName(request.getName());
            product.setDescription(request.getDescription());
            product.setStatus(request.getStatus());
            product.setBrand(brand);

            List<ProductImage> images = new ArrayList<>();
            if (request.getImages() != null) {
                for (ProductImageRequest imgReq : request.getImages()) {
                    ProductImage image = new ProductImage();
                    image.setUrl(imgReq.getUrl());
                    image.setIsPrimary(imgReq.getIsPrimary());
                    image.setProduct(product);
                    images.add(image);
                }
            }
            product.setImages(images);

            productRepository.save(product);
            return ApiResponse.success("Thành công", product.getId().toString());

        } catch (Exception e) {
            return ApiResponse.error("Lỗi: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<String> deleteProduct(Long id) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

            product.setDeletedAt(LocalDateTime.now());
            productRepository.save(product);

            return ApiResponse.success("Thành công", product.getId().toString());

        } catch (Exception e) {
            return ApiResponse.error("Lỗi: " + e.getMessage());
        }
    }

    private void validate(ProductRequest request) {
        Map<String, String> errors = new HashMap<>();

        if (request.getCode() == null || request.getCode().isBlank()) {
            errors.put("code", "Code không được để trống");
        }
        if (request.getName() == null || request.getName().isBlank()) {
            errors.put("name", "Tên không được để trống");
        }
        if (request.getStatus() == null) {
            errors.put("status", "Trạng thái không được để trống");
        }

        if (request.getImages() == null || request.getImages().isEmpty()) {
            errors.put("images", "Ảnh hoặc video không được để trống");
        } else {
            for (int i = 0; i < request.getImages().size(); i++) {
                ProductImageRequest img = request.getImages().get(i);
                if (img.getUrl() == null || img.getUrl().isBlank()) {
                    errors.put("images[" + i + "]", "Đường dẫn không được để trống");
                }
            }
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
