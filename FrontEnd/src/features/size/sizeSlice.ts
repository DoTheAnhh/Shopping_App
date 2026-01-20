import { createSlice, type PayloadAction } from "@reduxjs/toolkit";
import type { SizeResponse } from "../../types/size";

interface SizeState {
  items: SizeResponse[];
  loading: boolean;
  error: string | null;
}

const initialState: SizeState = {
  items: [],
  loading: false,
  error: null,
};

const sizeSlice = createSlice({
  name: "size",
  initialState,
  reducers: {
    fetchSizesStart(state) {
      state.loading = true;
      state.error = null;
    },
    fetchSizesSuccess(state, action: PayloadAction<SizeResponse[]>) {
      state.items = action.payload;
      state.loading = false;
    },
    fetchSizesError(state, action: PayloadAction<string>) {
      state.loading = false;
      state.error = action.payload;
    },
  },
});

export const {
  fetchSizesStart,
  fetchSizesSuccess,
  fetchSizesError,
} = sizeSlice.actions;

export default sizeSlice.reducer;