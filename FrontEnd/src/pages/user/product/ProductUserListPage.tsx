import { useEffect, useMemo, useState } from "react";
import "./css/ProductUserListPage.css";
import { useDispatch, useSelector } from "react-redux";
import { productApi } from "../../../api/productApi";
import type { RootState } from "../../../store/store";
import {
  fetchProductsError,
  fetchProductsStart,
  fetchProductsSuccess,
} from "../../../features/product/productSlice";

export default function ProductUserListPage() {
  const dispatch = useDispatch();
  const { items: products, loading } = useSelector(
    (state: RootState) => state.product
  );

  const [keyword, setKeyword] = useState("");
  const [visibleCount, setVisibleCount] = useState(15);

  const getStatusClass = (status: string) =>
  ({
    "Đang bán": "ACTIVE",
    "Ngừng bán": "INACTIVE",
    "Hết hàng": "OUT_OF_STOCK",
  }[status] || "INACTIVE");

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = async () => {
    dispatch(fetchProductsStart());
    try {
      const data = await productApi.list({});
      dispatch(fetchProductsSuccess(data));
    } catch (err: any) {
      dispatch(fetchProductsError(err.message));
    }
  };

  const filteredProducts = useMemo(() => {
    return products.filter((p: any) =>
      p.name.toLowerCase().includes(keyword.toLowerCase())
    );
  }, [products, keyword]);

  useEffect(() => {
    setVisibleCount(15);
  }, [keyword]);

  if (loading) return <div className="user-loading">Đang tải sản phẩm...</div>;

  return (
    <div className="user-product-page">
      <div className="content">
        <input
          className="search-box"
          placeholder="🔍 Tìm sản phẩm..."
          value={keyword}
          onChange={(e) => setKeyword(e.target.value)}
        />

        <div className="user-product-grid">
          {filteredProducts.slice(0, visibleCount).map((p: any) => {
            const primary =
              p.images?.find((i: any) => i.isPrimary) || p.images?.[0];

            const isVideo = primary?.url?.includes("/video/");

            const minPrice = p.productVariants?.length
              ? Math.min(...p.productVariants.map((v: any) => v.price))
              : null;

            return (
              <div className="user-product-card" key={p.id}>
                <div className="thumb">
                  {isVideo ? (
                    <video src={primary.url} muted loop />
                  ) : (
                    <img src={primary?.url} alt={p.name} />
                  )}
                </div>

                <div className="info">
                  <div className="name">{p.name}</div>
                  <div className="brand">{p.brandName}</div>

                  {minPrice && (
                    <div className="price">
                      {minPrice.toLocaleString()}đ
                    </div>
                  )}

                  <div className={`status-badge status-${getStatusClass(p.status)}`}>
                    {p.status}
                  </div>
                </div>
              </div>
            );
          })}
        </div>
        {visibleCount < filteredProducts.length && (
          <div className="load-more-wrapper">
            <button onClick={() => setVisibleCount(prev => prev + 15)}>
              Xem thêm
            </button>
          </div>
        )}
      </div>
    </div>
  );
}
