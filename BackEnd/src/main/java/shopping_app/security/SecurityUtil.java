package shopping_app.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import shopping_app.dto.current_user.response.CurrentUserResponse;

import java.util.Collections;
import java.util.List;

public class SecurityUtil {

    private SecurityUtil() {}

    public static CurrentUserResponse getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof CurrentUserResponse) {
            return (CurrentUserResponse) principal;
        }

        return null;
    }

    public static Long getUserId() {
        CurrentUserResponse user = getCurrentUser();
        return user != null ? user.getId() : null;
    }

    public static String getUsername() {
        CurrentUserResponse user = getCurrentUser();
        return user != null ? user.getUsername() : null;
    }

    public static String getFullName() {
        CurrentUserResponse user = getCurrentUser();
        return user != null ? user.getFullName() : null;
    }

    public static List<String> getRoles() {
        CurrentUserResponse user = getCurrentUser();
        return user != null && user.getRoles() != null
                ? user.getRoles()
                : Collections.emptyList();
    }

    public static boolean hasRole(String role) {
        return getRoles().contains(role);
    }

    public static boolean isAuthenticated() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }
}
