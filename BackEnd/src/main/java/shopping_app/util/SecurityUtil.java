package shopping_app.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import shopping_app.dto.current_user.response.CurrentUserResponse;

import java.util.Collections;
import java.util.List;

@Component
public class SecurityUtil {

    public SecurityUtil() {}

    public CurrentUserResponse getCurrentUser() {
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

    public Long getUserId() {
        CurrentUserResponse user = getCurrentUser();
        return user != null ? user.getId() : null;
    }

    public String getUsername() {
        CurrentUserResponse user = getCurrentUser();
        return user != null ? user.getUsername() : null;
    }

    public String getFullName() {
        CurrentUserResponse user = getCurrentUser();
        return user != null ? user.getFullName() : null;
    }

    public List<String> getRoles() {
        CurrentUserResponse user = getCurrentUser();
        return user != null && user.getRoles() != null
                ? user.getRoles()
                : Collections.emptyList();
    }

    public boolean hasRole(String role) {
        return getRoles().contains(role);
    }

    public boolean isAuthenticated() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }
}
