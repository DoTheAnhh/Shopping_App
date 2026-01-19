export interface BrandFilter {
  search?: string;
  code?: string;
  name?: string;
}

export interface BrandRequest {
  code?: string;
  name?: string;
}

export interface BrandResponse {
  id?: number,
  code?: string;
  name?: string;
}