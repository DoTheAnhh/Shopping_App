package shopping_app.entity.enums;

import lombok.Getter;

@Getter
public enum InvoiceStatus {

    PENDING("Chờ xác nhận"),
    CONFIRMED("Đã xác nhận"),
    SHIPPED("Đang giao"),
    COMPLETED("Hoàn thành"),
    CANCELLED("Đã hủy");

    private final String label;

    InvoiceStatus(String label) {
        this.label = label;
    }

    public boolean canChangeTo(InvoiceStatus next) {
        return switch (this) {
            case PENDING -> next == CONFIRMED || next == CANCELLED;
            case CONFIRMED -> next == SHIPPED || next == CANCELLED;
            case SHIPPED -> next == COMPLETED;
            case COMPLETED, CANCELLED -> false;
        };
    }
}
