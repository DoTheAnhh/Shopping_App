package shopping_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import shopping_app.entity.Color;

import java.util.List;
import java.util.Optional;

@Repository
public interface ColorRepository  extends JpaRepository<Color, Long>, JpaSpecificationExecutor<Color> {
    List<Color> findByDeletedAtIsNull();

    Optional<Color> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByCodeAndDeletedAtIsNull(String code);
}
