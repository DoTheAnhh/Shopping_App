import { createSlice, type PayloadAction } from "@reduxjs/toolkit";
import type { BrandResponse } from "../../types/brand";

interface BrandState {
  items: BrandResponse[];
  loading: boolean;
  error: string | null;
}

const initialState: BrandState = {
  items: [],
  loading: false,
  error: null,
};

const brandSlice = createSlice({
  name: "brand",
  initialState,
  reducers: {
    fetchBrandsStart(state) {
      state.loading = true;
      state.error = null;
    },
    fetchBrandsSuccess(state, action: PayloadAction<BrandResponse[]>) {
      state.items = action.payload;
      state.loading = false;
    },
    fetchBrandsError(state, action: PayloadAction<string>) {
      state.loading = false;
      state.error = action.payload;
    },
  },
});

export const {
  fetchBrandsStart,
  fetchBrandsSuccess,
  fetchBrandsError,
} = brandSlice.actions;

export default brandSlice.reducer;
