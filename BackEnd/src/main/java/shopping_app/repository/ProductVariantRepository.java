package shopping_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import shopping_app.entity.ProductVariant;

@Repository
public interface ProductVariantRepository  extends JpaRepository<ProductVariant, Long>, JpaSpecificationExecutor<ProductVariant> {
}
