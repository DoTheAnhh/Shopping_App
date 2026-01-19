import { useEffect, useState } from "react";
import { useNavigate, useParams, useLocation } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import type { ProductRequest, ProductResponse, ProductStatusRequest } from "../../../types/product";
import type { AppDispatch, RootState } from "../../../store/store";
import { productApi } from "../../../api/productApi";
import { brandApi } from "../../../api/brandApi";

import "./css/ProductFormPage.css";
import { fetchBrandsError, fetchBrandsStart, fetchBrandsSuccess } from "../../../features/brand/brandSlice";

/* ================== HELPERS ================== */
const isVideo = (url: string) => /\.(mp4|webm|ogg)$/i.test(url);

const mapStatusFromApi = (
  status?: ProductResponse["status"]
): ProductStatusRequest => {
  switch (status) {
    case "Đang bán":
      return "ACTIVE";
    case "Ngừng bán":
      return "INACTIVE";
    case "Hết hàng":
      return "OUT_OF_STOCK";
    default:
      return "ACTIVE";
  }
};

const getStatusLabel = (status: ProductStatusRequest) => {
  switch (status) {
    case "ACTIVE":
      return "Đang bán";
    case "INACTIVE":
      return "Ngừng bán";
    case "OUT_OF_STOCK":
      return "Hết hàng";
  }
};

const getStatusClass = (status: ProductStatusRequest) => {
  switch (status) {
    case "ACTIVE":
      return "status-active";
    case "INACTIVE":
      return "status-inactive";
    case "OUT_OF_STOCK":
      return "status-out";
  }
};

const mapProductToForm = (product: ProductResponse): ProductRequest => ({
  code: product.code,
  name: product.name,
  description: product.description,
  status: mapStatusFromApi(product.status),
  brandId: product.brandId,
  images: product.images.map(img => ({
    url: img.url,
    isPrimary: img.isPrimary,
  })),
});

export default function ProductFormPage() {
  const { id } = useParams<{ id: string }>();
  const location = useLocation();
  const navigate = useNavigate();
  const dispatch = useDispatch<AppDispatch>();

  const isCreate = location.pathname.includes("/create");
  const isEdit = location.pathname.includes("/edit/");
  const isDetail = !!id && !isCreate && !isEdit;
  const readOnly = isDetail;

  const brands = useSelector((state: RootState) => state.brand.items);

  const [form, setForm] = useState<ProductRequest>({
    code: "",
    name: "",
    description: "",
    status: "ACTIVE",
    brandId: undefined,
    images: [],
  });

  const [loading, setLoading] = useState(false);

  const STATUS_OPTIONS: ProductStatusRequest[] = [
    "ACTIVE",
    "INACTIVE",
    "OUT_OF_STOCK",
  ];

  /* LOAD PRODUCT */
  useEffect(() => {
    if (isCreate || !id) return;

    const fetchProduct = async () => {
      setLoading(true);
      try {
        const product = await productApi.getById(Number(id));
        setForm(mapProductToForm(product));
      } finally {
        setLoading(false);
      }
    };

    fetchProduct();
  }, [id, isCreate]);

  /* LOAD BRANDS */
  useEffect(() => {
    if (brands.length > 0) return;

    const fetchBrands = async () => {
      dispatch(fetchBrandsStart());
      try {
        const data = await brandApi.list({});
        dispatch(fetchBrandsSuccess(data));
      } catch (err: any) {
        dispatch(fetchBrandsError(err.message));
      }
    };

    fetchBrands();
  }, [dispatch, brands.length]);

  const handleChange = (
    e: React.ChangeEvent<
      HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement
    >
  ) => {
    const { name, value } = e.target;

    setForm(prev => ({
      ...prev,
      [name]:
        name === "brandId"
          ? value
            ? Number(value)
            : undefined
          : value,
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (isDetail) return;

    setLoading(true);
    try {
      if (isEdit && id) await productApi.update(Number(id), form);
      else await productApi.create(form);
      navigate("/product");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="product-form">
      <h1>
        {isCreate && "Tạo sản phẩm"}
        {isEdit && "Cập nhật sản phẩm"}
        {isDetail && "Chi tiết sản phẩm"}
      </h1>

      {loading && <div className="loading">Đang xử lý...</div>}

      {isDetail && (
        <div className={`status-badge ${getStatusClass(form.status)}`}>
          {getStatusLabel(form.status)}
        </div>
      )}

      <form onSubmit={handleSubmit}>
        {["code", "name"].map(field => (
          <div className="form-group" key={field}>
            <label>{field === "code" ? "Mã sản phẩm" : "Tên sản phẩm"}</label>
            <input
              name={field}
              value={(form as any)[field]}
              disabled={readOnly || loading}
              onChange={handleChange}
            />
          </div>
        ))}

        <div className="form-group">
          <label>Thương hiệu</label>
          <select
            name="brandId"
            value={form.brandId ?? ""}
            disabled={readOnly || loading}
            onChange={handleChange}
          >
            <option value="">-- Chọn --</option>
            {brands.map(b => (
              <option key={b.id} value={b.id}>
                {b.name}
              </option>
            ))}
          </select>
        </div>

        <div className="form-group">
          <label>Mô tả</label>
          <textarea
            name="description"
            value={form.description ?? ""}
            disabled={readOnly || loading}
            onChange={handleChange}
          />
        </div>

        <div className="form-group">
          <label>Trạng thái</label>
          <select
            name="status"
            value={form.status}
            disabled={readOnly || loading}
            onChange={handleChange}
          >
            {STATUS_OPTIONS.map(s => (
              <option key={s} value={s}>
                {getStatusLabel(s)}
              </option>
            ))}
          </select>
        </div>

        {form.images.length > 0 && (
          <div className="media-grid">
            {form.images.map((img, i) => (
              <div key={i} className="media-item">
                {isVideo(img.url) ? (
                  <video src={img.url} controls />
                ) : (
                  <img src={img.url} />
                )}
                {img.isPrimary && <span className="badge primary">PRIMARY</span>}
              </div>
            ))}
          </div>
        )}

        <div className="form-actions">
          {!isDetail && (
            <button type="submit" disabled={loading}>
              {loading ? "Đang lưu..." : "Lưu"}
            </button>
          )}
          {isDetail && (
            <button type="button" onClick={() => navigate(`/admin/product/edit/${id}`)}>
              ✏️ Sửa
            </button>
          )}
          <button type="button" onClick={() => navigate("/admin/product")}>
            Quay lại
          </button>
        </div>
      </form>
    </div>
  );
}
