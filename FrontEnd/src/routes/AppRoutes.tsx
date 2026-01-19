import { Routes, Route, Navigate } from "react-router-dom";
import { useSelector } from "react-redux";

import type { RootState } from "../store/store";

import LoginPage from "../pages/auth/LoginPage";
import { roleDefaultRoute } from "../utils/roleRoute";
import { dashboardRoutes } from "./DashboardRoutes";
import { adminProductRoutes, userProductRoutes } from "./ProductRoutes";

import AdminLayout from "../layout/admin/AdminLayout";
import { ProtectedRoute } from "./ProtectedRoute";
import UserLayout from "../layout/user/UserLayout";

export default function AppRoutes() {
  const { token, roles } = useSelector((state: RootState) => state.auth);

  const defaultRoute = () => {
    if (!token) return <Navigate to="/login" replace />;

    for (const role of roles) {
      const route = roleDefaultRoute[role];
      if (route) return <Navigate to={route} replace />;
    }

    return <Navigate to="/login" replace />;
  };

  return (
    <Routes>
      {/* Public */}
      <Route path="/login" element={<LoginPage />} />
      <Route path="/" element={defaultRoute()} />

      {/* ADMIN */}
      <Route
        path="/admin"
        element={
          <ProtectedRoute allowedRoles={["ADMIN"]}>
            <AdminLayout />
          </ProtectedRoute>
        }
      >
        {dashboardRoutes}
        {adminProductRoutes}
      </Route>

      {/* USER */}
      <Route
        path="/user"
        element={
          <ProtectedRoute allowedRoles={["USER"]}>
            <UserLayout />
          </ProtectedRoute>
        }
      >
        {userProductRoutes}
      </Route>

      {/* fallback */}
      <Route path="*" element={defaultRoute()} />
    </Routes>
  );
}
