package shopping_app.service.impl;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shopping_app.service.ICloudinaryService;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService implements ICloudinaryService {

    private final Cloudinary cloudinary;

    /**
     * Upload file (image hoặc video) lên Cloudinary.
     * Tự động xác định resource_type dựa vào content type của file.
     */
    public String uploadFile(MultipartFile file, String folder) {
        try {
            String contentType = file.getContentType(); // ví dụ "image/png" hoặc "video/mp4"
            String resourceType;

            if (contentType != null && contentType.startsWith("video")) {
                resourceType = "video";
            } else {
                resourceType = "image";
            }

            Map<?, ?> result = cloudinary.uploader().upload(
                    file.getBytes(),
                    Map.of(
                            "folder", folder,
                            "resource_type", resourceType
                    )
            );

            return result.get("secure_url").toString();

        } catch (Exception e) {
            throw new RuntimeException("Upload file failed: " + e.getMessage(), e);
        }
    }
}
