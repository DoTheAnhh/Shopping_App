import { Route } from "react-router-dom";
import ProductFormPage from "../pages/admin/product/ProductFormPage";
import ProductListPage from "../pages/admin/product/ProductListPage";
import ProductUserListPage from "../pages/user/product/ProductUserListPage";


export const adminProductRoutes = (
  <Route path="product">
    <Route index element={<ProductListPage />} />
    <Route path="create" element={<ProductFormPage />} />
    <Route path="edit/:id" element={<ProductFormPage />} />
  </Route>
);

export const userProductRoutes = (
  <Route path="product">
    <Route index element={<ProductUserListPage />} />
    {/* <Route path=":id" element={<ProductUserFormPage />} /> */}
  </Route>
);

