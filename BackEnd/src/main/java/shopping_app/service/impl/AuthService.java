package shopping_app.service.impl;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shopping_app.common.ApiResponse;
import shopping_app.dto.auth.request.LoginRequest;
import shopping_app.dto.auth.request.RegisterRequest;
import shopping_app.dto.auth.response.LoginResponse;
import shopping_app.entity.Role;
import shopping_app.entity.User;
import shopping_app.exception.BusinessException;
import shopping_app.repository.RoleRepository;
import shopping_app.repository.UserRepository;
import shopping_app.service.IAuthService;
import shopping_app.util.JwtUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public ApiResponse<LoginResponse> login(LoginRequest request) {

        User user = (User) userRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new BusinessException(
                                "Tài khoản không tồn tại",
                                HttpServletResponse.SC_NOT_FOUND
                        )
                );

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(
                    "Sai mật khẩu",
                    HttpServletResponse.SC_UNAUTHORIZED
            );
        }

        String accessToken = jwtUtil.generateToken(user);

        LoginResponse response = new LoginResponse(
                accessToken,
                user.getUsername(),
                user.getFullName()
        );

        return ApiResponse.success("Đăng nhập thành công", response);
    }

    @Override
    public ApiResponse<String> register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BusinessException(
                    "Username đã tồn tại",
                    HttpServletResponse.SC_CONFLICT // 409
            );
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setFullName(
                request.getUsername().contains("@")
                        ? request.getUsername().substring(0, request.getUsername().indexOf("@"))
                        : request.getUsername()
        );
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role userRole = roleRepository.findById(2L)
                .orElseThrow(() ->
                        new BusinessException(
                                "Chức vụ không tồn tại",
                                HttpServletResponse.SC_INTERNAL_SERVER_ERROR
                        )
                );

        user.setRoles(List.of(userRole));
        userRepository.save(user);

        return ApiResponse.success(
                "Đăng ký thành công",
                user.getId().toString()
        );
    }
}
