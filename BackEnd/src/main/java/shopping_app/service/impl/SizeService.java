package shopping_app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopping_app.entity.Size;
import shopping_app.repository.SizeRepository;
import shopping_app.service.ISizeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SizeService implements ISizeService {
    private final SizeRepository sizeRepository;
    @Override
    public List<Size> getAll() {
        return sizeRepository.findByDeletedAtIsNull();
    }

    @Override
    public Size getById(Long id) {
        return sizeRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Size not found"));
    }

    @Override
    public Size create(Size size) {
        if (sizeRepository.existsByCodeAndDeletedAtIsNull(size.getCode())) {
            throw new RuntimeException("Size code already exists");
        }
        return sizeRepository.save(size);
    }
}
