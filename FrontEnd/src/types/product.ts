export type ProductStatus = "ACTIVE" | "INACTIVE" | "OUT_OF_STOCK";

export interface ProductFilter {
  code?: string;
  name?: string;
  statuses?: ProductStatus[];
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
  status: ProductStatus;
  brandId?: number;
  images?: ProductImageRequest[];
}

export interface ProductResponse {
  id: number;
  code: string;
  name: string;
  description: string;
  status: ProductStatus;
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