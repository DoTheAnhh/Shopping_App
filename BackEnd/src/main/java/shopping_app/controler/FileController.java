package shopping_app.controler;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import shopping_app.common.ApiResponse;
import shopping_app.service.ICloudinaryService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
@Tag(name = "File") //Rename cho Swagger
public class FileController {

    private final ICloudinaryService cloudinaryService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ApiResponse<List<String>> uploadFiles(@RequestPart("files") List<MultipartFile> files) {
        List<String> urls = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                String url = cloudinaryService.uploadFile(file, "temp");
                urls.add(url);
            } catch (Exception e) {
                return ApiResponse.error("Upload thất bại: " + file.getOriginalFilename());
            }
        }

        return ApiResponse.success(urls);
    }
}
