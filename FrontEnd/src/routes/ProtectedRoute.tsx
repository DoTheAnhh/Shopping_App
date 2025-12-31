import { Navigate } from "react-router-dom";
import { useSelector } from "react-redux";
import type { ReactNode } from "react";
import type { RootState } from "../store/store";

interface Props {
  children: ReactNode;
  allowedRoles?: ("ADMIN" | "USER")[];
}

export const ProtectedRoute = ({ children, allowedRoles }: Props) => {
  const { token, roles } = useSelector((state: RootState) => state.auth);

  if (!token) return <Navigate to="/login" replace />;

  if (allowedRoles && !roles.some(r => allowedRoles.includes(r as "ADMIN" | "USER"))) {
    return <Navigate to="/" replace />;
  }

  return <>{children}</>;
};


