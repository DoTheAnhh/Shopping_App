export interface SizeFilter {
  search?: string;
  code?: string;
  name?: string;
}

export interface SizeRequest {
  code?: string;
  name?: string;
}

export interface SizeResponse {
  id?: number,
  code?: string;
  name?: string;
}