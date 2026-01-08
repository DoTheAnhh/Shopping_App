package shopping_app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shopping_app.common.BaseEntity;
import shopping_app.entity.enums.DiscountType;
import shopping_app.entity.enums.PromotionType;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "promotion")
public class Promotion  extends BaseEntity {

    private String code;

    private String name;

    @Enumerated(EnumType.STRING)
    private PromotionType promotionType;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    private Long discountValue;

    private Long minOrderValue; // áp dụng từ bao nhiêu tiền

    private Integer maxUsage;   // số lượt dùng tối đa

    private Integer usedCount;  // số lượt đã sử dụng

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    private Boolean isActive;

    private String description;
}
