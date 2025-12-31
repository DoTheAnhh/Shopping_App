import { useState } from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { setAuth } from "../../features/auth/authSlice";
import { login } from "../../api/authApi";
import { decodeToken } from "../../utils/jwt";

export default function LoginPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const handleLogin = async () => {
    try {
      const res = await login(username, password);
      const token = res.data.token;

      const payload = decodeToken(token);
      if (!payload) return;

      dispatch(
        setAuth({ 
          token, 
          roles: payload.roles as ("ADMIN" | "USER")[] 
        })
      );

    localStorage.setItem("token", token);
    localStorage.setItem("roles", JSON.stringify(payload.roles));

    // redirect dựa vào role
    if (payload.roles.includes("ADMIN")) {
      navigate("/dashboard");
    } else if (payload.roles.includes("USER")) {
      navigate("/product");
    } else {
      alert("Bạn không có quyền truy cập");
    }
    } catch (err: any) {
      alert(err.response?.data?.message || "Login failed");
    }
  };

  return (
    <div>
      <input placeholder="Username" value={username} onChange={e => setUsername(e.target.value)} />
      <input placeholder="Password" type="password" value={password} onChange={e => setPassword(e.target.value)} />
      <button onClick={handleLogin}>Login</button>
    </div>
  );
}
