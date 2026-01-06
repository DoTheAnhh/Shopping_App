package shopping_app.mapper;

import shopping_app.dto.brand.response.BrandResponse;
import shopping_app.entity.Brand;

import java.util.List;

public class BrandMapper {

    public static BrandResponse toResponse(Brand brand) {
        if (brand == null) return null;

        BrandResponse res = new BrandResponse();
        res.setId(brand.getId());
        res.setCode(brand.getCode());
        res.setName(brand.getName());

        return res;
    }

    public static List<BrandResponse> toResponses(List<Brand> brands) {
        if (brands == null || brands.isEmpty()) {
            return List.of();
        }

        return brands.stream()
                .map(BrandMapper::toResponse)
                .toList();
    }
}
