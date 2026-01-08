package shopping_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import shopping_app.entity.Size;

import java.util.List;
import java.util.Optional;

@Repository
public interface SizeRepository  extends JpaRepository<Size, Long>, JpaSpecificationExecutor<Size> {
    List<Size> findByDeletedAtIsNull();

    Optional<Size> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByCodeAndDeletedAtIsNull(String code);
}
