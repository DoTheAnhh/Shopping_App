package shopping_app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopping_app.common.ApiResponse;
import shopping_app.dto.role.response.RoleResponse;
import shopping_app.entity.Role;
import shopping_app.repository.RoleRepository;
import shopping_app.service.IRoleService;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;

    @Override
    public ApiResponse<RoleResponse> getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chức vụ không tồn tại với id: " + id));

        RoleResponse response = new RoleResponse();
        response.setName(role.getName());

        return ApiResponse.success(response);
    }
}
