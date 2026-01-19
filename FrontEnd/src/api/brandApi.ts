import api from "./axios/axiosConfig";
import type { ApiResponse } from "../types/apiResponse";
import type { BrandFilter, BrandResponse } from "../types/brand";

export const brandApi = {
    list: async (payload: BrandFilter): Promise<BrandResponse[]> => {
    const res = await api.post<ApiResponse<BrandResponse[]>>("/brand/list", payload);
    return res.data.data;
  },
}