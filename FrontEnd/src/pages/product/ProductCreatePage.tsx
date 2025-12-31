import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { productApi } from "../../api/productApi";
import { fileApi } from "../../api/fileApi";
import type { ProductRequest, ProductStatus, ProductImageRequest } from "../../types/product";

export default function ProductCreatePage() {
  const navigate = useNavigate();
  const [form, setForm] = useState<ProductRequest>({
    code: "",
    name: "",
    description: "",
    status: "ACTIVE" as ProductStatus,
    brandId: 0,
    images: [],
  });

  const [files, setFiles] = useState<File[]>([]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: value }));
  };

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files) setFiles(Array.from(e.target.files));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      let uploadedImages: ProductImageRequest[] = [];
      if (files.length) {
        const urls = await fileApi.upload(files);
        uploadedImages = urls.map(url => ({ url, isPrimary: false }));
      }
      await productApi.create({ ...form, images: uploadedImages });
      alert("Tạo thành công");
      navigate("/product");
    } catch (err: any) {
      console.error(err);
      alert(err.message || "Failed to create product");
    }
  };

  return (
    <div>
      <h1>Tạo Product mới</h1>
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
          <label>Images:</label>
          <input type="file" multiple accept="image/*" onChange={handleFileChange} />
        </div>
        <button type="submit">Tạo</button>
      </form>
    </div>
  );
}
