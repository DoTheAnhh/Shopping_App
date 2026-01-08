package shopping_app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopping_app.entity.Color;
import shopping_app.repository.ColorRepository;
import shopping_app.service.IColorService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ColorService implements IColorService {

    private final ColorRepository colorRepository;
    @Override
    public List<Color> getAll() {
        return colorRepository.findByDeletedAtIsNull();
    }

    @Override
    public Color getById(Long id) {
        return colorRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Color not found"));
    }

    @Override
    public Color create(Color color) {
        if (colorRepository.existsByCodeAndDeletedAtIsNull(color.getCode())) {
            throw new RuntimeException("Color code already exists");
        }
        return colorRepository.save(color);
    }


}
