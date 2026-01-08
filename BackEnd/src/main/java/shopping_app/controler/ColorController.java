package shopping_app.controler;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shopping_app.entity.Color;
import shopping_app.service.IColorService;

import java.util.List;

@RestController
@RequestMapping("/api/color")
@RequiredArgsConstructor
public class ColorController {
    private final IColorService colorService;

    @GetMapping
    public List<Color> getAll(){
        return colorService.getAll();
    }
    @PostMapping
    public Color create (@RequestBody Color color){
        return colorService.create(color);
    }
    @GetMapping("/{id}")
    public Color getColorById(Long id){
        return colorService.getById(id);
    }
}
