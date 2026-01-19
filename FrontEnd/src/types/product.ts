export type ProductStatusRequest = "ACTIVE" | "INACTIVE" | "OUT_OF_STOCK";

export type ProductStatusResponse = "Đang bán" | "Ngừng bán" | "Hết hàng";

export interface ProductFilter {
  search?: string;
  code?: string;
  name?: string;
  statuses?: ProductStatusRequest[];
  brandIds?: number[];
  priceFrom?: number;
  priceTo?: number;
  createdAtFrom?: string;
  createdAtTo?: string;
  brandName?: string;
}

export interface ProductRequest {
  code: string;
  name: string;
  description?: string;
  status: ProductStatusRequest;
  brandId?: number;
  images: ProductImageRequest[];
}

export interface ProductResponse {
  id: number;
  code: string;
  name: string;
  description: string;
  status: ProductStatusResponse;
  brandId: number;
  brandName: string;
  images: ProductImageResponse[];
}

export interface ProductImageRequest {
  url: string;
  isPrimary: boolean;
}

export interface ProductImageResponse {
  url: string;
  isPrimary: boolean;
}