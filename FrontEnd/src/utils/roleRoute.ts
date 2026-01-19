import type { Role } from "../types/role";

export const roleDefaultRoute: Record<Role, string> = {
  ADMIN: "/admin/dashboard",
  USER: "/user/product",
  SHIPPER: "/shipper/shipper",
};