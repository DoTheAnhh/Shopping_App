import api from "axios";
import { store } from "../../store/store";

const apiInstance = api.create({
  baseURL: "http://10.15.184.15:8080",
});

apiInstance.interceptors.request.use((config) => {
  const token = store.getState().auth.token;
  if (token) {
    config.headers = config.headers ?? {} as any; 
    (config.headers as any)["Authorization"] = `Bearer ${token}`;
  }
  return config;
});

export default apiInstance; 
