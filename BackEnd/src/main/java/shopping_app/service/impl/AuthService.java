package shopping_app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shopping_app.common.ApiResponse;
import shopping_app.dto.auth.request.LoginRequest;
import shopping_app.dto.auth.request.RegisterRequest;
import shopping_app.dto.auth.response.LoginResponse;
import shopping_app.entity.Role;
import shopping_app.entity.User;
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
        User user = (User) userRepository.findByUsername(request.getUsername()).orElse(null);

        if (user == null) {
            return ApiResponse.error("Tài khoản không tồn tại");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ApiResponse.error("Sai tài khoản hoặc mật khẩu");
        }

        String accessToken = jwtUtil.generateToken(user);
        LoginResponse response = new LoginResponse(
                accessToken,
                user.getUsername(),
                user.getFullName()
        );
        return ApiResponse.success(response);
    }

    @Override
    public ApiResponse<String> register(RegisterRequest request) {
        if (userRepository.existsByUsername((request.getUsername()))) {
            return ApiResponse.error("Username đã tồn tại");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setFullName(request.getUsername().contains("@")
                ? request.getUsername().substring(0, request.getUsername().indexOf("@"))
                : request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role userRole = roleRepository.findById(2L)
                .orElseThrow(() -> new RuntimeException("Chức vụ không tồn tại"));
        user.setRoles(List.of(userRole));

        userRepository.save(user);

        return ApiResponse.success("Thành công", user.getId().toString());
    }
}
