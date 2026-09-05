package id_ocr.controller;

import id_ocr.dto.MetadatasResponse;
import id_ocr.entities.ImageData;
import id_ocr.mapper.ImageDataToMetadata;
import id_ocr.services.ImageService;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DownloadImageControllerTest {

    @Mock
    private ImageService imageService;

    @Mock
    private ImageDataToMetadata imageDataToMetadata;

    @InjectMocks
    private DownloadImageController controller;

    @Test
    void shouldGetImageDataTest() {
        String uid = "0c9778cc";

        ImageData imageData = new ImageData();
        imageData.setId(1L);

        MetadatasResponse expectedResponse = mock(MetadatasResponse.class);

        when(imageService.getImageByUid(uid)).thenReturn(imageData);
        when(imageDataToMetadata.toEntity(imageData)).thenReturn(expectedResponse);

        MetadatasResponse actualResponse = controller.getImageData(uid);

        assertSame(expectedResponse, actualResponse);
        verify(imageService).getImageByUid(uid);
        verify(imageDataToMetadata).toEntity(imageData);
    }

    @Test
    void shouldGetImageTest() {
        String uid = "0c9778cc";

        byte[] imageBytes = "abc".getBytes();

        ImageData imageData = new ImageData();
        imageData.setContentType("image/png");
        imageData.setFileName("test.png");
        imageData.setImageData(imageBytes);

        when(imageService.getImageByUid(uid)).thenReturn(imageData);

        ResponseEntity<byte[]> actualResponse = controller.getImage(uid);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertArrayEquals(imageBytes, actualResponse.getBody());
        assertEquals(MediaType.IMAGE_PNG, actualResponse.getHeaders().getContentType());

        String contentDisposition =
                actualResponse.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION);

        assertNotNull(contentDisposition);
        assertTrue(contentDisposition.contains("attachment"));
        assertTrue(contentDisposition.contains("test.png"));

        verify(imageService).getImageByUid(uid);
    }
}
