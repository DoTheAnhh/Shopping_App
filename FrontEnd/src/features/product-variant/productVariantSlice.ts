import { createSlice, type PayloadAction } from "@reduxjs/toolkit";
import type { ProductVariantResponse } from "../../types/product-variant";

interface ProductVariantState {
  items: ProductVariantResponse[];
  loading: boolean;
  error: string | null;
}

const initialState: ProductVariantState = {
  items: [],
  loading: false,
  error: null,
};

const productVariantSlice = createSlice({
  name: "productVariant",
  initialState,
  reducers: {
    fetchProductVariantsStart(state) {
      state.loading = true;
      state.error = null;
    },
    fetchProductVariantsSuccess(
      state,
      action: PayloadAction<ProductVariantResponse[]>
    ) {
      state.items = action.payload;
      state.loading = false;
    },
    fetchProductVariantsError(state, action: PayloadAction<string>) {
      state.loading = false;
      state.error = action.payload;
    },

    clearProductVariants(state) {
      state.items = [];
    },
  },
});

export const {
  fetchProductVariantsStart,
  fetchProductVariantsSuccess,
  fetchProductVariantsError,
  clearProductVariants,
} = productVariantSlice.actions;

export default productVariantSlice.reducer;
