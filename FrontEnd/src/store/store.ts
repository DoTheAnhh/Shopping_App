import { configureStore } from "@reduxjs/toolkit";
import authReducer from "../features/auth/authSlice";
import productReducer from "../features/product/productSlice";
import brandReducer from "../features/brand/brandSlice";

export const store = configureStore({
  reducer: {
    auth: authReducer,
    product: productReducer,
    brand: brandReducer,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
