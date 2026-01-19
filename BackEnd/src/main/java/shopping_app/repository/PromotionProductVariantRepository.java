package shopping_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import shopping_app.entity.ProductVariant;
import shopping_app.entity.Promotion;
import shopping_app.entity.PromotionProductVariant;

@Repository
public interface PromotionProductVariantRepository extends JpaRepository<PromotionProductVariant, Long>, JpaSpecificationExecutor<PromotionProductVariant> {
    boolean existsByPromotionAndProductVariant(Promotion promotion, ProductVariant productVariant);
}
