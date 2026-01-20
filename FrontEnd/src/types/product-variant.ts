export interface ProductVariantFilter {
  search?: string;
  productId?: number;
  sizeId?: number;
  colorId?: number;
  priceFrom?: number;
  priceTo?: number;
  isHasStock?: boolean;
  createdAtFrom?: string;
  createdAtTo?: string;
}

export interface ProductVariantRequest {
  productId: number;
  sizeId: number;
  colorId: number;
  price: number;
  stock: number;
}

export interface ProductVariantResponse {
  id: number;
  productName: string;
  sizeId: number;
  sizeName: string;
  colorId: number;
  colorName: string;
  price: string;
  stock: string;
  productImages: ProductImageResponse[];
}

export interface ProductImageResponse {
  url: string;
  isPrimary: boolean;
}
