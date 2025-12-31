import type { ProductFilter, ProductResponse } from "../../types/product";
import api from "../../api/axios/axiosConfig";

export const getProductsApi = async (filter: ProductFilter): Promise<ProductResponse[]> => {
  const res = await api.post("/product/list", filter);
  return res.data.data;
};