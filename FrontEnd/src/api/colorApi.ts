import api from "./axios/axiosConfig";
import type { ApiResponse } from "../types/apiResponse";
import type { ColorFilter, ColorResponse } from "../types/color";

export const colorApi = {
    list: async (payload: ColorFilter): Promise<ColorResponse[]> => {
    const res = await api.post<ApiResponse<ColorResponse[]>>("/color/list", payload);
    return res.data.data;
  },
}