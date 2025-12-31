import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { Link } from "react-router-dom";
import type { RootState } from "../../store/store";
import { productApi } from "../../api/productApi";
import type { ProductResponse } from "../../types/product";

export default function ProductListPage() {
  const [products, setProducts] = useState<ProductResponse[]>([]);
  const { roles } = useSelector((state: RootState) => state.auth);

  const isAdmin = roles.includes("ADMIN");

  const fetchProducts = async () => {
    const data = await productApi.list();
    setProducts(data);
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  const handleDelete = async (id: number) => {
    if (!window.confirm("Bạn có chắc chắn muốn xoá không ?")) return;
    await productApi.delete(id);
    setProducts(prev => prev.filter(p => p.id !== id));
  };

  return (
    <div>
      <h1>Products</h1>
      {isAdmin && <Link to="/product/create">Tạo mới</Link>}
      <ul>
        {products.map(p => (
          <li key={p.id}>
            {p.name} ({p.code}) - <Link to={`/product/${p.id}`}>Chi tiết</Link>
            {isAdmin && (
              <>
                {" "} | <Link to={`/product/edit/${p.id}`}>Cập nhật</Link>
                {" "} | <button onClick={() => handleDelete(p.id)}>Xoá</button>
              </>
            )}
          </li>
        ))}
      </ul>
    </div>
  );
}
