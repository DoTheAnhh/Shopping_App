import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";

import "./css/ProductListPage.css";
import type { RootState } from "../../../store/store";
import type { ProductFilter } from "../../../types/product";
import { fetchProductsError, fetchProductsStart, fetchProductsSuccess, removeProduct } from "../../../features/product/productSlice";
import { productApi } from "../../../api/productApi";
import ConfirmDialog from "../../../components/ConfirmDialog";

export default function ProductListPage() {
  const dispatch = useDispatch();

  const { items: products, loading, error } = useSelector(
    (state: RootState) => state.product
  );
  const { roles } = useSelector((state: RootState) => state.auth);
  const isAdmin = roles.includes("ADMIN");

  const [keyword, setKeyword] = useState("");
  const [deleteId, setDeleteId] = useState<number | null>(null);

  const fetchProducts = async (filter: ProductFilter = {}) => {
    dispatch(fetchProductsStart());
    try {
      const data = await productApi.list(filter);
      dispatch(fetchProductsSuccess(data));
    } catch (err: any) {
      dispatch(fetchProductsError(err.message));
    }
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  useEffect(() => {
    const timer = setTimeout(() => {
      fetchProducts({
        search: keyword || undefined
      });
    }, 400);

    return () => clearTimeout(timer);
  }, [keyword]);

  const handleConfirmDelete = async () => {
    if (!deleteId) return;
    await productApi.delete(deleteId);
    dispatch(removeProduct(deleteId));
    setDeleteId(null);
  };

  if (loading) return <p className="page-loading">Đang tải...</p>;
  if (error) return <p className="page-error">{error}</p>;

  return (
    <div className="product-list">
      {/* ===== HEADER ===== */}
      <div className="product-header">
        <h1>Sản phẩm</h1>

        <div className="header-actions">
          {/* SEARCH */}
          <div className="search-box">
            <input
              type="text"
              placeholder="Tìm theo mã hoặc tên..."
              value={keyword}
              onChange={e => setKeyword(e.target.value)}
            />
          </div>

          {/* CREATE */}
          {isAdmin && (
            <Link to="/admin/product/create" className="btn-create">
              + Tạo mới
            </Link>
          )}
        </div>
      </div>

      {/* ===== LIST ===== */}
      {products.length === 0 ? (
        <p className="empty-text">Không có sản phẩm</p>
      ) : (
        <ul className="product-items">
          {products.map(p => {
            const primaryImage = p.images?.find(i => i.isPrimary);

            return (
              <li key={p.id} className="product-card">
                {/* IMAGE */}
                <div className="product-thumb">
                  {primaryImage ? (
                    <img src={primaryImage.url} alt={p.name} />
                  ) : (
                    <div className="no-image">No image</div>
                  )}
                </div>

                {/* INFO */}
                <div className="product-info">
                  <div className="product-name">{p.name}</div>
                  <div className="product-meta">
                    <span>{p.code}</span>
                    <span>• {p.brandName}</span>
                    <span className={`status-tag ${mapStatus(p.status)}`}>
                      {p.status}
                    </span>
                  </div>
                </div>

                {/* ACTIONS */}
                <div className="product-actions">
                  <Link to={`/admin/product/${p.id}`} className="btn-outline">
                    Chi tiết
                  </Link>

                  {isAdmin && (
                    <>
                      <Link to={`/admin/product/edit/${p.id}`} className="btn-outline">
                        Sửa
                      </Link>
                      <button
                        className="btn-danger"
                        onClick={() => setDeleteId(p.id)}
                      >
                        Xoá
                      </button>
                    </>
                  )}
                </div>      
              </li>
            );
          })}
        </ul>
      )}

      <ConfirmDialog
        open={deleteId !== null}
        title="Xoá sản phẩm"
        message="Bạn có chắc chắn muốn xoá sản phẩm này?"
        onCancel={() => setDeleteId(null)}
        onConfirm={handleConfirmDelete}
      />
    </div>
  );
}

/* map status tiếng Việt → class */
function mapStatus(status: string) {
  switch (status) {
    case "Đang bán":
      return "active";
    case "Ngừng bán":
      return "inactive";
    case "Hết hàng":
      return "out_of_stock";
    default:
      return "";
  }
}