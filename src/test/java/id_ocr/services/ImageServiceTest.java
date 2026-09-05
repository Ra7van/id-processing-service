package id_ocr.services;

import id_ocr.entities.IdentityCard;
import id_ocr.entities.ImageData;
import id_ocr.repositories.IdentityCardRepository;
import id_ocr.repositories.ImageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    @Mock
    private IdentityCardRepository identityCardRepository;

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageService imageService;

    @Test
    void shouldGetImageByUidTest() {
        String uid = "0c9778cc";

        IdentityCard result = new IdentityCard();
        result.setUid(uid);
        result.setId(1L);

        ImageData expectedImageData = new ImageData();
        expectedImageData.setId(1L);

        when(identityCardRepository.findByUid(uid)).thenReturn(Optional.of(result));
        when(imageRepository.findById(result.getId())).thenReturn(Optional.of(expectedImageData));

        ImageData actualImageData = imageService.getImageByUid(uid);

        assertSame(expectedImageData, actualImageData);
        verify(identityCardRepository).findByUid(uid);
        verify(imageRepository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenImageNotFoundTest() {
        String uid = "0c9778cc";

        when(identityCardRepository.findByUid(uid)).thenReturn(Optional.empty());

        RuntimeException actualException = assertThrows(
                RuntimeException.class,
                () -> imageService.getImageByUid(uid)
        );

        assertEquals("Identity card not found", actualException.getMessage());
        verify(identityCardRepository).findByUid(uid);
        verifyNoInteractions(imageRepository);
    }

    @Test
    void shouldThrowExceptionWhenImageDataNotFoundTest() {
        String uid = "0c9778cc";

        IdentityCard result = new IdentityCard();
        result.setUid(uid);
        result.setId(1L);

        when(identityCardRepository.findByUid(uid)).thenReturn(Optional.of(result));
        when(imageRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException actualException = assertThrows(
                RuntimeException.class,
                () -> imageService.getImageByUid(uid)
        );

        assertEquals("Image not found", actualException.getMessage());
        verify(identityCardRepository).findByUid(uid);
        verify(imageRepository).findById(1L);
    }

    @Test
    void shouldSaveIdentityCardWithImageDataTest() throws IOException {
        IdentityCard identityCard = new IdentityCard();
        IdentityCard result = new IdentityCard();
        result.setId(1L);

        MultipartFile file = mock(MultipartFile.class);

        when(identityCardRepository.save(identityCard)).thenReturn(result);
        when(file.getBytes()).thenReturn("image-content".getBytes());
        when(file.getContentType()).thenReturn("image/png");
        when(file.getOriginalFilename()).thenReturn("front.png");
        when(file.getSize()).thenReturn(1024L);

        imageService.saveIdentityCardWithImage(identityCard, file);

        verify(identityCardRepository).save(identityCard);
        verify(imageRepository).save(any(ImageData.class));
    }

    @Test
    void shouldThrowExceptionWhenTryingToSaveIdentityCardWithImageDataTest() throws IOException {
        IdentityCard identityCard = new IdentityCard();
        IdentityCard result = new IdentityCard();

        MultipartFile file = mock(MultipartFile.class);

        when(identityCardRepository.save(identityCard)).thenReturn(result);
        when(file.getBytes()).thenThrow(new IOException("read failed"));

        RuntimeException actualException = assertThrows(
                RuntimeException.class,
                () -> imageService.saveIdentityCardWithImage(identityCard, file)
        );

        assertEquals("Could not save image", actualException.getMessage());
        assertInstanceOf(IOException.class, actualException.getCause());

        verify(identityCardRepository).save(identityCard);
        verifyNoInteractions(imageRepository);
    }
}