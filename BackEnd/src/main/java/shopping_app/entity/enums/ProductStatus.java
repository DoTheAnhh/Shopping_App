package shopping_app.entity.enums;

import lombok.Getter;

@Getter
public enum ProductStatus {

    ACTIVE("Đang bán"),
    INACTIVE("Ngừng bán"),
    OUT_OF_STOCK("Hết hàng");

    private final String label;

    ProductStatus(String label) {
        this.label = label;
    }
}
