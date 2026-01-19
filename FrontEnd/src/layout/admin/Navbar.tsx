import { useNavigate } from "react-router-dom";
import "./css/Navbar.css";

export default function Navbar() {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    navigate("/login");
  };

  return (
    <div className="navbar">
      <div className="navbar-left">
        <span className="logo" onClick={() => navigate("/")}>
          Do The Anh
        </span>
      </div>

      <div className="navbar-right">
        <button className="btn-logout" onClick={handleLogout}>
          Đăng xuất
        </button>
      </div>
    </div>
  );
}
