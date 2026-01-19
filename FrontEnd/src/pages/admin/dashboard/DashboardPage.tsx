import { Link } from "react-router-dom";
import "./css/Dashboard.css"

export default function DashboardPage() {
  return (
    <div>
      <h1>Dashboard (Admin)</h1>
      <nav>
        <Link to="/admin/product">Products</Link> | <Link to="/admin/user">Users</Link>
      </nav>
    </div>
  );
}
