package shopping_app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shopping_app.common.BaseEntity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "size")
public class Size extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String code;
    @Column(nullable = false)
    private String name;
}
