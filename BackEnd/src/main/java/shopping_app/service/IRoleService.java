package shopping_app.service;

import shopping_app.common.ApiResponse;
import shopping_app.dto.role.response.RoleResponse;

public interface IRoleService {

    ApiResponse<RoleResponse> getRoleById(Long id);
}
