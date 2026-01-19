import { createSlice, type PayloadAction } from "@reduxjs/toolkit";
import type { ProductResponse } from "../../types/product";

interface ProductState {
  items: ProductResponse[];
  loading: boolean;
  error: string | null;
}

const initialState: ProductState = {
  items: [],
  loading: false,
  error: null,
};

const productSlice = createSlice({
  name: "product",
  initialState,
  reducers: {
    fetchProductsStart(state) {
      state.loading = true;
      state.error = null;
    },
    fetchProductsSuccess(state, action: PayloadAction<ProductResponse[]>) {
      state.items = action.payload;
      state.loading = false;
    },
    fetchProductsError(state, action: PayloadAction<string>) {
      state.loading = false;
      state.error = action.payload;
    },
    removeProduct(state, action: PayloadAction<number>) {
      state.items = state.items.filter(p => p.id !== action.payload);
    },
  },
});

export const {
  fetchProductsStart,
  fetchProductsSuccess,
  fetchProductsError,
  removeProduct,
} = productSlice.actions;

export default productSlice.reducer;
