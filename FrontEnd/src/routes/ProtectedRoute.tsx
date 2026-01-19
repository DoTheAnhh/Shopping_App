import type { ReactNode } from "react";
import { Navigate } from "react-router-dom";
import { useSelector } from "react-redux";

import type { Role } from "../types/role";
import type { RootState } from "../store/store";

interface Props {
  children: ReactNode;
  allowedRoles?: Role[];
}

export const ProtectedRoute = ({ children, allowedRoles }: Props) => {
  const { token, roles } = useSelector((state: RootState) => state.auth);

  // Chưa login → về login
  if (!token) {
    return <Navigate to="/login" replace />;
  }

  // Nếu KHÔNG truyền allowedRoles → chỉ cần login là được vào
  if (!allowedRoles || allowedRoles.length === 0) {
    return <>{children}</>;
  }

  // Có allowedRoles → mới check quyền
  const hasPermission = roles.some(role => allowedRoles.includes(role));

  if (!hasPermission) {
    return <Navigate to="/" replace />;
  }

  return <>{children}</>;
};
