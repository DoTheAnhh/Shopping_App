import { createSlice, type PayloadAction } from "@reduxjs/toolkit";
import type { ColorResponse } from "../../types/color";

interface ColorState {
  items: ColorResponse[];
  loading: boolean;
  error: string | null;
}

const initialState: ColorState = {
  items: [],
  loading: false,
  error: null,
};

const colorSlice = createSlice({
  name: "color",
  initialState,
  reducers: {
    fetchColorsStart(state) {
      state.loading = true;
      state.error = null;
    },
    fetchColorsSuccess(state, action: PayloadAction<ColorResponse[]>) {
      state.items = action.payload;
      state.loading = false;
    },
    fetchColorsError(state, action: PayloadAction<string>) {
      state.loading = false;
      state.error = action.payload;
    },
  },
});

export const {
  fetchColorsStart,
  fetchColorsSuccess,
  fetchColorsError,
} = colorSlice.actions;

export default colorSlice.reducer;