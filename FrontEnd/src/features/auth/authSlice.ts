import { createSlice, type PayloadAction } from "@reduxjs/toolkit";

interface AuthState {
  token: string | null;
  roles: ("ADMIN" | "USER")[];
}

const tokenFromStorage = localStorage.getItem("token");
const rolesFromStorage = localStorage.getItem("roles");

const initialState: AuthState = {
  token: tokenFromStorage || null,
  roles: rolesFromStorage ? JSON.parse(rolesFromStorage) : [],
};

export const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    setAuth: (state, action: PayloadAction<{ token: string; roles: ("ADMIN" | "USER")[] }>) => {
      state.token = action.payload.token;
      state.roles = action.payload.roles;

      // Lưu vào localStorage
      localStorage.setItem("token", action.payload.token);
      localStorage.setItem("roles", JSON.stringify(action.payload.roles));
    },
    logout: (state) => {
      state.token = null;
      state.roles = [];
      localStorage.removeItem("token");
      localStorage.removeItem("roles");
    },
  },
});

export const { setAuth, logout } = authSlice.actions;
export default authSlice.reducer;
