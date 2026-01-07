package shopping_app.service;

import shopping_app.entity.Size;

import java.util.List;

public interface ISizeService {
    List<Size> getAll();

    Size getById(Long id);

    Size create(Size size);


}
