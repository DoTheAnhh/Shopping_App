import { useEffect, useState } from "react";
import { useParams, useLocation } from "react-router-dom";
import { productApi } from "../../api/productApi";
import type { ProductResponse } from "../../types/product";

export default function ProductDetailPage() {
  const { id } = useParams<{ id: string }>();
  const location = useLocation();
  const stateProduct = location.state as ProductResponse | undefined;

  const [product, setProduct] = useState<ProductResponse | null>(stateProduct || null);

  useEffect(() => {
    if (!product && id) {
      const fetchProduct = async () => {
        try {
          const data = await productApi.getById(Number(id));
          setProduct(data);
        } catch (err) {
          console.error(err);
        }
      };
      fetchProduct();
    }
  }, [id, product]);

  if (!product) return <p>Loading...</p>;

  return (
    <div>
      <h1>{product.name}</h1>
      <p>Code: {product.code}</p>
      <p>Description: {product.description}</p>
      <p>Status: {product.status}</p>

      {product.images && product.images.length > 0 && (
        <div style={{ display: "flex", gap: "10px", marginTop: "10px" }}>
          {product.images.map((img, index) => (
            <div key={index}>
              <img src={img.url} alt={`Product ${index}`} width={150} />
              {img.isPrimary && <p>Primary</p>}
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
