import api from "./axios/axiosConfig";
import type { ProductFilter, ProductRequest, ProductResponse } from "../types/product";
import type { ApiResponse } from "../types/apiResponse";

export const productApi = {
  list: async (payload: ProductFilter): Promise<ProductResponse[]> => {
    const res = await api.post<ApiResponse<ProductResponse[]>>("/product/list", payload);
    return res.data.data;
  },

  getById: async (id: number): Promise<ProductResponse> => {
    const res = await api.get<ApiResponse<ProductResponse>>(`/product/${id}`);
    return res.data.data;
  },

  create: async (payload: ProductRequest) => {
    const res = await api.post<ApiResponse<null>>("/product", payload);
    if (res.data.status !== "SUCCESS") {
      throw new Error(res.data.message);
    }
  },

  update: async (id: number, payload: ProductRequest) => {
    const res = await api.put<ApiResponse<null>>(`/product/${id}`, payload);
    if (res.data.status !== "SUCCESS") {
      throw new Error(res.data.message);
    }
  },

  delete: async (id: number) => {
    const res = await api.delete<ApiResponse<null>>(`/product/${id}`);
    if (res.data.status !== "SUCCESS") {
      throw new Error(res.data.message);
    }
  },
};
