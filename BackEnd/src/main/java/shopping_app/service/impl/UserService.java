package shopping_app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopping_app.entity.User;
import shopping_app.repository.UserRepository;
import shopping_app.service.IUserService;
import shopping_app.util.SecurityUtil;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final SecurityUtil securityUtil;
    private final UserRepository userRepository;

    @Override
    public User getCurrentUserEntity() {
        Long userId = securityUtil.getUserId();

        if (userId == null) {
            throw new RuntimeException("Người dùng chưa đăng nhập");
        }

        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));
    }
}
