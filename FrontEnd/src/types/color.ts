export interface ColorFilter {
  search?: string;
  code?: string;
  name?: string;
}

export interface ColorRequest {
  code?: string;
  name?: string;
}

export interface ColorResponse {
  id?: number,
  code?: string;
  name?: string;
}