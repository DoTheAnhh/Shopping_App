import type { ApiResponse } from "../types/apiResponse";
import type { ProductVariantFilter, ProductVariantResponse } from "../types/product-variant";
import api from "./axios/axiosConfig";

export const productVariantApi = {
  list: async (payload: ProductVariantFilter): Promise<ProductVariantResponse[]> => {
    const res = await api.post<ApiResponse<ProductVariantResponse[]>>("/product-variant/list", payload);
    return res.data.data;
  },
}