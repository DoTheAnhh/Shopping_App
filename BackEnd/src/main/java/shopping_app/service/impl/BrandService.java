package shopping_app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import shopping_app.common.ApiResponse;
import shopping_app.dto.brand.request.BrandFilter;
import shopping_app.dto.brand.request.BrandRequest;
import shopping_app.dto.brand.response.BrandResponse;
import shopping_app.entity.Brand;
import shopping_app.exception.ValidationException;
import shopping_app.mapper.BrandMapper;
import shopping_app.repository.BrandRepository;
import shopping_app.service.IBrandService;
import shopping_app.specification.GenericSpecification;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BrandService implements IBrandService {

    private final BrandRepository brandRepository;
    private final GenericSpecification<Brand> specification;
    private final BrandMapper mapper;

    @Override
    public ApiResponse<List<BrandResponse>> getBrands(BrandFilter filter) {
        Specification<Brand> spec = specification.build(filter);

        List<Brand> brands = brandRepository.findAll(spec);

        List<BrandResponse> responses = mapper.toResponses(brands);

        return ApiResponse.success(responses);
    }

    @Override
    public ApiResponse<BrandResponse> getBrandById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Thương hiệu không tồn tại với id: " + id));

        BrandResponse response = mapper.toResponse(brand);
        return ApiResponse.success(response);
    }

    @Override
    public ApiResponse<String> createBrand(BrandRequest request) {
        try {
            validate(request);

            Brand brand = new Brand();
            brand.setCode(request.getCode());
            brand.setName(request.getName());

            brandRepository.save(brand);

            return ApiResponse.success("Thành công", brand.getId().toString());

        } catch (Exception e) {
            return ApiResponse.error("Lỗi: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<String> updateBrand(BrandRequest request, Long id) {
        try {
            Brand brand = brandRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Thương hiệu không tồn tại"));

            validate(request);

            brand.setCode(request.getCode());
            brand.setName(request.getName());

            brandRepository.save(brand);
            return ApiResponse.success("Thành công", brand.getId().toString());

        } catch (Exception e) {
            return ApiResponse.error("Lỗi: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<String> deleteBrand(Long id) {
        try {
            Brand brand = brandRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Thương hiệu không tồn tại"));

            brand.setDeletedAt(LocalDateTime.now());
            brandRepository.save(brand);

            return ApiResponse.success("Thành công", brand.getId().toString());

        } catch (Exception e) {
            return ApiResponse.error("Lỗi: " + e.getMessage());
        }
    }

    private void validate(BrandRequest request) {
        Map<String, String> errors = new HashMap<>();

        if (request.getCode() == null || request.getCode().isBlank()) {
            errors.put("code", "Mã không được để trống");
        }
        if (request.getName() == null || request.getName().isBlank()) {
            errors.put("name", "Tên không được để trống");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
