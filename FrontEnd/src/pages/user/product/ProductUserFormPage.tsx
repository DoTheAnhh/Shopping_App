import { useEffect, useMemo, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useParams } from "react-router-dom";
import "./css/ProductUserFormPage.css";
import type { RootState } from "../../../store/store";
import { productVariantApi } from "../../../api/productVariantApi";
import {
  fetchProductVariantsError,
  fetchProductVariantsStart,
  fetchProductVariantsSuccess,
} from "../../../features/product-variant/productVariantSlice";

export default function ProductUserFormPage() {
  const dispatch = useDispatch();
  const { productId } = useParams();

  const { items: variants, loading, error } = useSelector(
    (state: RootState) => state.productVariant
  );

  const [selectedSize, setSelectedSize] = useState<number | null>(null);
  const [selectedColor, setSelectedColor] = useState<number | null>(null);
  const [quantity, setQuantity] = useState(1);
  const [mediaIndex, setMediaIndex] = useState(0);
  const [videoThumbs, setVideoThumbs] = useState<Record<string, string>>({});

  const VIDEO_FALLBACK =
    "https://cdn-icons-png.flaticon.com/512/727/727245.png";

  const formatVND = (value?: string | number) =>
    value ? `${value} VND` : "Sản phẩm đã hết hàng";

  const isVideoUrl = (url: string) => /\.(mp4|webm|ogg|mov)$/i.test(url);

  const createVideoThumbnail = (url: string): Promise<string> => {
    return new Promise((resolve) => {
      try {
        const video = document.createElement("video");
        video.src = url;
        video.crossOrigin = "anonymous";
        video.muted = true;
        video.playsInline = true;
        video.preload = "auto";
        video.style.position = "absolute";
        video.style.left = "-9999px";

        document.body.appendChild(video);

        video.onloadeddata = () => {
          video.currentTime = 0.1;
        };

        video.onseeked = () => {
          const canvas = document.createElement("canvas");
          canvas.width = video.videoWidth;
          canvas.height = video.videoHeight;

          const ctx = canvas.getContext("2d");
          if (ctx) {
            ctx.drawImage(video, 0, 0, canvas.width, canvas.height);
            resolve(canvas.toDataURL("image/png"));
          } else resolve(VIDEO_FALLBACK);

          document.body.removeChild(video);
        };

        video.onerror = () => {
          resolve(VIDEO_FALLBACK);
          document.body.removeChild(video);
        };
      } catch {
        resolve(VIDEO_FALLBACK);
      }
    });
  };

  const fetchVariants = async () => {
    dispatch(fetchProductVariantsStart());
    try {
      const data = await productVariantApi.list({
        productId: Number(productId),
      });
      dispatch(fetchProductVariantsSuccess(data));
    } catch (err: any) {
      dispatch(fetchProductVariantsError(err.message));
    }
  };

  useEffect(() => {
    fetchVariants();
  }, [productId]);

  const sizes = useMemo(
    () =>
      Array.from(
        new Map(variants.map((v) => [v.sizeId, v.sizeName])).entries()
      ),
    [variants]
  );

  const colors = useMemo(
    () =>
      Array.from(
        new Map(variants.map((v) => [v.colorId, v.colorName])).entries()
      ),
    [variants]
  );

  useEffect(() => {
    if (sizes.length && !selectedSize) setSelectedSize(sizes[0][0]);
    if (colors.length && !selectedColor) setSelectedColor(colors[0][0]);
  }, [sizes, colors]);

  const selectedVariant = useMemo(
    () =>
      variants.find(
        (v) => v.sizeId === selectedSize && v.colorId === selectedColor
      ) || null,
    [variants, selectedSize, selectedColor]
  );

  const mediaList = useMemo(() => {
    if (!variants.length) return [];
    const all = variants.flatMap((v) => v.productImages || []);
    return all.filter(
      (v, i, arr) => arr.findIndex((x) => x.url === v.url) === i
    );
  }, [variants]);

  useEffect(() => setMediaIndex(0), [mediaList]);

  useEffect(() => {
    const load = async () => {
      const videos = mediaList.filter((m) => isVideoUrl(m.url));
      const newMap: Record<string, string> = {};

      for (const v of videos) {
        if (!videoThumbs[v.url]) {
          newMap[v.url] = await createVideoThumbnail(v.url);
        }
      }
      if (Object.keys(newMap).length)
        setVideoThumbs((p) => ({ ...p, ...newMap }));
    };
    load();
  }, [mediaList]);

  const currentMedia = mediaList[mediaIndex];

  if (loading) return <p>Đang tải sản phẩm...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div className="product-detail-page">
      <div className="left">
        {currentMedia ? (
          isVideoUrl(currentMedia.url) ? (
            <video src={currentMedia.url} controls className="media-view" />
          ) : (
            <img src={currentMedia.url} className="media-view" />
          )
        ) : (
          <div className="no-image">No media</div>
        )}

        <div className="media-thumbs">
          {mediaList.map((m, i) => {
            let thumb = m.url;

            if (isVideoUrl(m.url)) {
              thumb =
                selectedVariant?.productImages?.find((x) => !isVideoUrl(x.url))?.url ||
                selectedVariant?.productImages?.[0]?.url ||
                "https://cdn-icons-png.flaticon.com/512/727/727245.png";
            }
            return (
              <img
                key={i}
                src={thumb}
                className={mediaIndex === i ? "active" : ""}
                onClick={() => setMediaIndex(i)}
              />
            );
          })}
        </div>
        {mediaList.length > 1 && (
          <div className="media-nav">
            <button onClick={() => setMediaIndex(p => p === 0 ? mediaList.length - 1 : p - 1)}>◀</button>
            <button onClick={() => setMediaIndex(p => p === mediaList.length - 1 ? 0 : p + 1)}>▶</button>
          </div>
        )}
      </div>

      <div className="right">
        <h2>{selectedVariant?.productName || "Sản phẩm"}</h2>
        <h3>{formatVND(selectedVariant?.price)}</h3>

        <div className="variant-group">
          <b>Chọn kích thước</b>
          <div className="variant-options">
            {sizes.map(([id, name]) => (
              <button key={id} className={selectedSize === id ? "active" : ""} onClick={() => setSelectedSize(id)}>{name}</button>
            ))}
          </div>
        </div>

        <div className="variant-group">
          <b>Chọn màu sắc</b>
          <div className="variant-options color-fixed">
            {colors.map(([id, name]) => (
              <button key={id} className={selectedColor === id ? "active" : ""} onClick={() => setSelectedColor(id)}>{name}</button>
            ))}
          </div>
        </div>

        <div className="stock-text">
          {selectedVariant ? `Kho còn: ${selectedVariant.stock}` : "Sản phẩm hết hàng"}
        </div>

        <div className="qty-box">
          <span>Số lượng</span>
          <input
            type="number"
            min={1}
            max={Number(selectedVariant?.stock) || 1}
            value={quantity}
            disabled={!selectedVariant}
            onChange={(e) =>
              setQuantity(Math.min(Number(selectedVariant?.stock) || 1, Math.max(1, Number(e.target.value))))
            }
          />
        </div>

        <button className="add-cart-btn" disabled={!selectedVariant}>
          {selectedVariant ? `Thêm vào giỏ hàng (variantId: ${selectedVariant.id})` : "Hết hàng"}
        </button>
      </div>
    </div>
  );
}
