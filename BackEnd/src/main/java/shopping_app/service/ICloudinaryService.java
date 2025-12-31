package shopping_app.service;

import org.springframework.web.multipart.MultipartFile;

public interface ICloudinaryService {

    String uploadFile(MultipartFile file, String folder);
}
