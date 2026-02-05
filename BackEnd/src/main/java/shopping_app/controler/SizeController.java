package shopping_app.controler;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shopping_app.entity.Size;
import shopping_app.service.ISizeService;

import java.util.List;

@RestController
@RequestMapping("/api/sizes")
@RequiredArgsConstructor
public class SizeController {
    private final ISizeService sizeService;

    @GetMapping
    public List<Size> getAll(){
        return sizeService.getAll();
    }
    @PostMapping
    public Size create(@RequestBody Size size){
        return sizeService.create(size);
    }

    @GetMapping("/{id}")
    public Size getById(Long id){
        return sizeService.getById(id);
    }
}
