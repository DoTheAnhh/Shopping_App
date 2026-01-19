import { jwtDecode } from "jwt-decode";
import type { Role } from "../types/role";

export interface TokenPayload {
  sub: string;
  roles: Role[];
  iat: number;
  exp: number;
}

export const decodeToken = (token: string): TokenPayload | null => {
  try {
    return jwtDecode<TokenPayload>(token);
  } catch {
    return null;
  }
};