import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { productApi } from "../../api/productApi";
import { fileApi } from "../../api/fileApi";
import type { ProductRequest, ProductResponse, ProductStatus, ProductImageRequest } from "../../types/product";

export default function ProductEditPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const [form, setForm] = useState<ProductRequest>({
    code: "",
    name: "",
    description: "",
    status: "ACTIVE" as ProductStatus,
    brandId: 0,
    images: [],
  });

  const [existingImages, setExistingImages] = useState<ProductImageRequest[]>([]);
  const [files, setFiles] = useState<File[]>([]);

  useEffect(() => {
    const fetchProduct = async () => {
      if (!id) return;
      try {
        const data: ProductResponse = await productApi.getById(Number(id));
        setForm({
          code: data.code,
          name: data.name,
          description: data.description,
          status: data.status,
          brandId: 0,
          images: [],
        });
        setExistingImages(data.images.map(img => ({ url: img.url, isPrimary: img.isPrimary })));
      } catch (err) {
        console.error(err);
        alert("Failed to load product");
      }
    };
    fetchProduct();
  }, [id]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: value }));
  };

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
  setFiles(prev => [...prev, ...(e.target.files ? Array.from(e.target.files) : [])]);
};

  const handleRemoveExistingImage = (url: string) => {
    setExistingImages(prev => prev.filter(img => img.url !== url));
  };

  const handleRemoveNewFile = (index: number) => {
  setFiles(prev => prev.filter((_, i) => i !== index));
};

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!id) return;

    try {
      let uploadedImages: ProductImageRequest[] = [];
      if (files.length > 0) {
        const urls = await fileApi.upload(files);
        uploadedImages = urls.map(url => ({ url, isPrimary: false }));
      }

      const allImages = [...existingImages, ...uploadedImages];
      await productApi.update(Number(id), { ...form, images: allImages });

      alert("Cập nhật thành công");
      navigate("/product");
    } catch (err: any) {
      console.error(err);
      alert(err.message || "Failed to update product");
    }
  };

  return (
    <div>
      <h1>Cập nhật Product</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Code:</label>
          <input name="code" value={form.code} onChange={handleChange} required />
        </div>
        <div>
          <label>Name:</label>
          <input name="name" value={form.name} onChange={handleChange} required />
        </div>
        <div>
          <label>Description:</label>
          <textarea name="description" value={form.description} onChange={handleChange} />
        </div>
        <div>
          <label>Status:</label>
          <select name="status" value={form.status} onChange={handleChange}>
            <option value="ACTIVE">ACTIVE</option>
            <option value="INACTIVE">INACTIVE</option>
            <option value="OUT_OF_STOCK">OUT_OF_STOCK</option>
          </select>
        </div>
        <div>
          <label>Brand ID:</label>
          <input type="number" name="brandId" value={form.brandId} onChange={handleChange} required />
        </div>

        <div>
          <label>Existing Images:</label>
          <div style={{ display: "flex", gap: "10px", flexWrap: "wrap" }}>
            {existingImages.map(img => (
              <div key={img.url} style={{ position: "relative" }}>
                <img src={img.url} alt="" width={100} />
                <button
                  type="button"
                  onClick={() => handleRemoveExistingImage(img.url)}
                  style={{ display: "block", marginTop: "5px" }}
                >
                  Xóa
                </button>
              </div>
            ))}

            {files.map((file, index) => (
              <div key={index} style={{ position: "relative" }}>
                <img src={URL.createObjectURL(file)} alt="" width={100} />
                <button
                  type="button"
                  onClick={() => handleRemoveNewFile(index)}
                  style={{ display: "block", marginTop: "5px" }}
                >
                  Xóa
                </button>
              </div>
            ))}
          </div>
        </div>

        <div>
          <label>Upload New Images:</label>
          <input type="file" multiple accept="image/*" onChange={handleFileChange} />
        </div>

        <button type="submit">Cập nhật</button>
      </form>
    </div>
  );
}
