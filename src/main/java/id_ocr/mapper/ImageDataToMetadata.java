package id_ocr.mapper;

import id_ocr.dto.MetadatasResponse;
import id_ocr.entities.ImageData;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ImageDataToMetadata {

    private final Logger log = LoggerFactory.getLogger(ImageDataToMetadata.class);

    public MetadatasResponse toEntity (ImageData imageData) {
        MetadatasResponse metadatasResponse = new MetadatasResponse();

        metadatasResponse.setImageData(imageData.getImageData());
        metadatasResponse.setContentType(imageData.getContentType());
        metadatasResponse.setFileName(imageData.getFileName());
        metadatasResponse.setFileSize(imageData.getFileSize());
        metadatasResponse.setCreatedAt(imageData.getCreatedAt());

        log.info("Metadata retrieved successfully");
        return metadatasResponse;
    }
}
