import api from "../api/axios/axiosConfig";
import type { ProductFilter, ProductRequest, ProductResponse } from "../types/product";


export const productApi = {
  list: async (filter: ProductFilter = {}): Promise<ProductResponse[]> => {
    const res = await api.post("/product/list", filter);
    return res.data.data;
  },

  getById: async (id: number): Promise<ProductResponse> => {
    const res = await api.get(`/product/${id}`);
    return res.data.data
  },

  create: async (data: ProductRequest): Promise<{ success: boolean; message: string }> => {
    const res = await api.post("/product/create", data);
    return {
      success: res.data.status === "success",
      message: res.data.message,
    };
  },

  update: async (id: number, data: ProductRequest): Promise<{ success: boolean; message: string }> => {
    const res = await api.patch(`/product/update/${id}`, data);
    return {
      success: res.data.status === "success",
      message: res.data.message,
    };
  },

  delete: async (id: number): Promise<{ success: boolean; message: string }> => {
    const res = await api.delete(`/product/delete/${id}`);
    return {
      success: res.data.status === "success",
      message: res.data.message,
    };
  },
};