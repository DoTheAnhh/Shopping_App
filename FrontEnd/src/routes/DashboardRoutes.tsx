import { Route } from "react-router-dom";
import { ProtectedRoute } from "./ProtectedRoute";
import DashboardPage from "../pages/admin/dashboard/DashboardPage";

export const dashboardRoutes = (
  <Route
    path="/admin/dashboard"
    element={
      <ProtectedRoute allowedRoles={["ADMIN"]}>
        <DashboardPage />
      </ProtectedRoute>
    }
  />
);