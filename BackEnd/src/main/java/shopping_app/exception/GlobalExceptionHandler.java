package shopping_app.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shopping_app.common.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleAccessDenied(AccessDeniedException ex) {
        ApiResponse<?> body = ApiResponse.error("Bạn không có quyền truy cập");
        return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body(body);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<?>> handleValidation(ValidationException ex) {
        ApiResponse<?> body = ApiResponse.error("Dữ liệu không hợp lệ", ex.getErrors());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleInternalServerError(Exception ex) {
        ApiResponse<?> body = ApiResponse.error("Đã xảy ra lỗi, vui lòng thử lại sau");
        return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(body);
    }
}
