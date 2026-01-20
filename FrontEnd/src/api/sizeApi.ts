import api from "./axios/axiosConfig";
import type { ApiResponse } from "../types/apiResponse";
import type { SizeFilter, SizeResponse } from "../types/size";

export const sizeApi = {
    list: async (payload: SizeFilter): Promise<SizeResponse[]> => {
    const res = await api.post<ApiResponse<SizeResponse[]>>("/size/list", payload);
    return res.data.data;
  },
}