import { Route } from "react-router-dom";
import ProductFormPage from "../pages/admin/product/ProductFormPage";
import ProductListPage from "../pages/admin/product/ProductListPage";
import ProductUserListPage from "../pages/user/product/ProductUserListPage";
import ProductUserFormPage from "../pages/user/product/ProductUserFormPage";


export const adminProductRoutes = (
  <Route path="product">
    <Route index element={<ProductListPage />} />
    <Route path="create" element={<ProductFormPage />} />
    <Route path="edit/:id" element={<ProductFormPage />} />
    <Route path=":id" element={<ProductFormPage />} />
  </Route>
);

export const userProductRoutes = (
  <Route path="product">
    <Route index element={<ProductUserListPage />} />
    <Route path="productId/:productId" element={<ProductUserFormPage />} />
    {/* <Route path=":id" element={<ProductUserFormPage />} /> */}
  </Route>
);

