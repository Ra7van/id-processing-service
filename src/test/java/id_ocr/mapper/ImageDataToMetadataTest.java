package id_ocr.mapper;

import id_ocr.dto.MetadatasResponse;
import id_ocr.entities.ImageData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ImageDataToMetadataTest {
    
    private ImageDataToMetadata imageDataToMetadata;

    @BeforeEach
    void setUp() {
        imageDataToMetadata = new ImageDataToMetadata();
    }

    @Test
    void shouldMapImageDataToMetadatasResponseTest() {
        byte[] imageBytes = "abc".getBytes();
        LocalDateTime createdAt = LocalDateTime.of(2024, 1, 10, 12, 30);

        ImageData imageData = new ImageData();
        imageData.setImageData(imageBytes);
        imageData.setContentType("image/png");
        imageData.setFileName("test.png");
        imageData.setFileSize(123L);
        imageData.setCreatedAt(createdAt);

        MetadatasResponse actualResult = imageDataToMetadata.toEntity(imageData);

        assertArrayEquals(imageBytes, actualResult.getImageData());
        assertEquals("image/png", actualResult.getContentType());
        assertEquals("test.png", actualResult.getFileName());
        assertEquals(123L, actualResult.getFileSize());
        assertEquals(createdAt, actualResult.getCreatedAt());
    }
}