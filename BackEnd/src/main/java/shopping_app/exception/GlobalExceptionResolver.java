package shopping_app.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import shopping_app.common.ApiResponse;

import java.io.IOException;
import java.util.List;

@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    private static final List<String> SWAGGER_PATHS = List.of(
            "/swagger-ui",
            "/swagger-ui.html",
            "/v3/api-docs",
            "/swagger-resources",
            "/webjars"
    );

    private boolean isSwaggerRequest(HttpServletRequest request) {
        String path = request.getServletPath();
        return SWAGGER_PATHS.stream().anyMatch(path::startsWith);
    }

    @Override
    public ModelAndView resolveException(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex) {

        // ❗ Cho swagger tự xử lý
        if (isSwaggerRequest(request)) {
            return null;
        }

        ApiResponse<?> body;
        int status;

        // ✅ CHỈ xử lý exception tầng controller / service
        if (ex instanceof ValidationException validationEx) {
            body = ApiResponse.error("Dữ liệu không hợp lệ", validationEx.getErrors());
            status = HttpServletResponse.SC_BAD_REQUEST;
        } else {
            body = ApiResponse.error("Đã xảy ra lỗi, vui lòng thử lại sau");
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }

        writeResponse(response, status, body);
        return new ModelAndView();
    }

    private void writeResponse(HttpServletResponse response, int status, ApiResponse<?> body) {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        try {
            response.getWriter().write(
                    new com.fasterxml.jackson.databind.ObjectMapper()
                            .writeValueAsString(body)
            );
        } catch (IOException e) {
            // ignore
        }
    }
}
