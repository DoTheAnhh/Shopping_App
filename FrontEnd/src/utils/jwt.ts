import { jwtDecode } from "jwt-decode";

interface TokenPayload {
  sub: string;
  roles: string[];
  iat: number;
  exp: number;
}

export const decodeToken = (token: string): TokenPayload | null => {
  try {
    return jwtDecode<TokenPayload>(token);
  } catch (e) {
    console.error("Không tìm thấy token", e);
    return null;
  }
};