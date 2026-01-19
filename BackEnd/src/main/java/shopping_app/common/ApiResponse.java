package shopping_app.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shopping_app.entity.enums.ApiStatus;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private ApiStatus status;
    private String message;
    private T data;
    private Map<String, String> errors;

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(ApiStatus.success, message, data, null);
    }

    @Deprecated
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ApiStatus.success, "Success", data, null);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(ApiStatus.error, message, null, null);
    }

    public static <T> ApiResponse<T> error(String message, Map<String, String> errors) {
        return new ApiResponse<>(ApiStatus.error, message, null, errors);
    }
}
