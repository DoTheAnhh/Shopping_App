package shopping_app.service;

import shopping_app.entity.Color;

import java.util.List;

public interface IColorService {
    List<Color> getAll();

    Color getById(Long id);

    Color create(Color color);


}
