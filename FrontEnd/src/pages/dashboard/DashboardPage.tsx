import { Link } from "react-router-dom";

export default function DashboardPage() {
  return (
    <div>
      <h1>Dashboard (Admin)</h1>
      <nav>
        <Link to="/product">Products</Link> | <Link to="/users">Users</Link>
      </nav>
    </div>
  );
}
