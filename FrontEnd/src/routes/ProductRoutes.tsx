import { Route } from "react-router-dom";
import { ProtectedRoute } from "./ProtectedRoute";
import ProductListPage from "../pages/product/ProductListPage";
import ProductDetailPage from "../pages/product/ProductDetailPage";
import ProductCreatePage from "../pages/product/ProductCreatePage";
import ProductEditPage from "../pages/product/ProductEditPage";

export const productRoutes = [
  <Route
    key="product-list"
    path="/product"
    element={
      <ProtectedRoute allowedRoles={["ADMIN", "USER"]}>
        <ProductListPage />
      </ProtectedRoute>
    }
  />,
  <Route
    key="product-detail"
    path="/product/:id"
    element={
      <ProtectedRoute allowedRoles={["ADMIN", "USER"]}>
        <ProductDetailPage />
      </ProtectedRoute>
    }
  />,
  <Route
    key="product-create"
    path="/product/create"
    element={
      <ProtectedRoute allowedRoles={["ADMIN"]}>
        <ProductCreatePage />
      </ProtectedRoute>
    }
  />,
  <Route
    key="product-edit"
    path="/product/edit/:id"
    element={
      <ProtectedRoute allowedRoles={["ADMIN"]}>
        <ProductEditPage />
      </ProtectedRoute>
    }
  />,
];