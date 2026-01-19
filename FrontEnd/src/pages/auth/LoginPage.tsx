import { useState } from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";

import { setAuth } from "../../features/auth/authSlice";
import { login } from "../../api/authApi";
import { decodeToken } from "../../utils/jwt";

import "./css/LoginPage.css";
import { roleDefaultRoute } from "../../utils/roleRoute";

export default function LoginPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleLogin = async () => {
    setErrorMessage("");
    setSuccessMessage("");

    try {    
      const res = await login(username, password);
      const { status, message, data } = res;      

      if (status !== "success") {        
        setErrorMessage(message);
        return;
      }

      const token = data.accessToken;
      const payload = decodeToken(token);
      if (!payload) return;

      dispatch(
        setAuth({
          token,
          roles: payload.roles,
        })
      );

      setSuccessMessage(message);

      const targetRoute = payload.roles
        .map(role => roleDefaultRoute[role])
        .find(Boolean);

      if (targetRoute) {
        navigate(targetRoute, { replace: true });
      }

    } catch (err: any) {
      setErrorMessage(err.response?.data?.message);
    }
  };

  return (
    <div className="login-page">
      <div className="login-card">
        <h2 className="login-title">Welcome Back</h2>
        <p className="login-subtitle">Please sign in to continue</p>

        <div className="login-field">
          <label>Username</label>
          <input
            value={username}
            onChange={e => setUsername(e.target.value)}
            placeholder="Enter your username"
          />
        </div>

        <div className="login-field">
          <label>Password</label>
          <input
            type="password"
            value={password}
            onChange={e => setPassword(e.target.value)}
            placeholder="Enter your password"
          />
        </div>

        {errorMessage && <div className="login-error">{errorMessage}</div>}
        {successMessage && <div className="login-success">{successMessage}</div>}

        <div className="login-actions">
          <button className="login-link" onClick={() => navigate("/forgot-password")}>
            Quên mật khẩu?
          </button>
          <button className="login-link" onClick={() => navigate("/register")}>
            Đăng ký tài khoản
          </button>
        </div>

        <button className="login-button" onClick={handleLogin}>
          Sign In
        </button>
      </div>
    </div>
  );
}
