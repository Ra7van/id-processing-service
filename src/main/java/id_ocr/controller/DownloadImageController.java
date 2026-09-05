package id_ocr.controller;

import id_ocr.dto.MetadatasResponse;
import id_ocr.entities.ImageData;
import id_ocr.mapper.ImageDataToMetadata;
import id_ocr.services.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DownloadImageController {

    private final ImageService imageService;
    private final ImageDataToMetadata imageDataToMetadata;
    private final Logger log = LoggerFactory.getLogger(DownloadImageController.class);

    public DownloadImageController(
            ImageService imageService,
            ImageDataToMetadata imageDataToMetadata
    ) {
        this.imageService = imageService;
        this.imageDataToMetadata = imageDataToMetadata;
    }

    @GetMapping("/image/metadata/{uid}")
    public MetadatasResponse getImageData(@PathVariable String uid) {
        log.info("Trying to retrieve metadata for the requested image");

        return imageDataToMetadata.toEntity(imageService.getImageByUid(uid));
    }

    @GetMapping("/image/{uid}")
    public ResponseEntity<byte[]> getImage(@PathVariable String uid) {
        log.info("Trying to retrieve the requested image");

        ImageData imageData = imageService.getImageByUid(uid);

        log.info("Image data retrieved");
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(imageData.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + imageData.getFileName() + "\"")
                .body(imageData.getImageData());
    }
}