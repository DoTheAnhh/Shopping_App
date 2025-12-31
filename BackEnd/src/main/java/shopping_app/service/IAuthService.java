package shopping_app.service;

import shopping_app.common.ApiResponse;
import shopping_app.dto.auth.request.LoginRequest;
import shopping_app.dto.auth.request.RegisterRequest;
import shopping_app.dto.auth.response.LoginResponse;

public interface IAuthService {

    ApiResponse<LoginResponse> login(LoginRequest request);

    ApiResponse<String> register(RegisterRequest request);
}
