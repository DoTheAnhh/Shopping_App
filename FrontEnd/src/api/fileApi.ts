import api from "./axios/axiosConfig";

export const fileApi = {
  upload: async (files: File[]): Promise<string[]> => {
    const formData = new FormData();
    files.forEach(file => formData.append("files", file));

    const res = await api.post("/file/upload", formData, {
      headers: { "Content-Type": "multipart/form-data" },
    });

    if (res.data.status === "success") return res.data.data;
    throw new Error(res.data.message || "Failed to upload files");
  },
};