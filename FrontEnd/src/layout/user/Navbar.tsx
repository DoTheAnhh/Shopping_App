import { Link } from "react-router-dom";
import "./css/Navbar.css";

export default function Navbar() {
  return (
    <header className="u-navbar">
      <div className="u-nav-inner">
        <Link to="/user/product" className="u-logo">
          Do The Anh
        </Link>

        <nav className="u-menu">
          <Link to="/user/product">Sản phẩm</Link>
          <Link to="/user/product">Hàng mới</Link>
          <Link to="/user/product">Bán chạy</Link>
          <Link to="/user/product">Khuyến mãi</Link>
        </nav>

        <div className="u-actions">
          <Link to="/user/cart" className="cart">🛒</Link>

          <div className="u-user">
            <span className="avatar">👤</span>
            <div className="dropdown">
              <Link to="/user/profile">Tài khoản</Link>
              <Link to="/login">Đăng xuất</Link>
            </div>
          </div>
        </div>
      </div>
    </header>
  );
}