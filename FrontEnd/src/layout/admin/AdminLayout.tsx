import { Outlet } from "react-router-dom";
import Navbar from "../admin/Navbar";

export default function AdminLayout() {
  return (
    <>
      <Navbar />
      <div className="page-container">
        <Outlet />
      </div>
    </>
  );
}