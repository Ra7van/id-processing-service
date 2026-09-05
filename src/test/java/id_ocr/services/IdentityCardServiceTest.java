package id_ocr.services;

import id_ocr.dto.IdCardExtractionResult;
import id_ocr.dto.ResponseIdentityCard;
import id_ocr.entities.IdentityCard;
import id_ocr.mapper.ExtractionToIdentityCard;
import id_ocr.mapper.IdentityCardToResponseIdentityCard;
import id_ocr.repositories.IdentityCardRepository;
import id_ocr.util.IdentityCardProperties;
import id_ocr.util.IdentityCardUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IdentityCardServiceTest {

    @Mock
    private ImageService imageService;

    @Mock
    private OcrService ocrService;

    @Mock
    private IdentityCardRepository identityCardRepository;

    @Mock
    private IdentityCardUtil identityCardUtil;

    @Mock
    private ExtractionToIdentityCard extractionToIdentityCard;

    @Mock
    private IdentityCardToResponseIdentityCard identityCardToResponseIdentityCard;

    @Mock
    private IdentityCardProperties identityCardProperties;

    @InjectMocks
    private IdentityCardService identityCardService;

    @Test
    void shouldScanAndSaveHappyTest() {
        MultipartFile file1 = mock(MultipartFile.class);
        MultipartFile file2 = mock(MultipartFile.class);

        IdCardExtractionResult result = mock(IdCardExtractionResult.class);
        IdentityCard entity = new IdentityCard();
        IdentityCard savedId = new IdentityCard();
        ResponseIdentityCard expectedResponse = new ResponseIdentityCard();

        when(ocrService.ocr(file1, file2)).thenReturn(result);
        when(identityCardUtil.validate(result)).thenReturn(true);
        when(extractionToIdentityCard.toEntity(result)).thenReturn(entity);
        when(identityCardRepository.save(entity)).thenReturn(savedId);
        when(identityCardToResponseIdentityCard.toEntity(entity)).thenReturn(expectedResponse);

        ResponseIdentityCard actualResponse = identityCardService.scanAndSave(file1, file2);

        assertSame(expectedResponse, actualResponse);
        verify(ocrService).ocr(file1, file2);
        verify(identityCardUtil).validate(result);
        verify(extractionToIdentityCard).toEntity(result);
        verify(identityCardRepository).save(entity);
        verify(imageService).saveIdentityCardWithImage(savedId, file1);
        verify(identityCardToResponseIdentityCard).toEntity(entity);
    }

    @Test
    void shouldThrowExceptionWhenCnpIsInvalidAndInvalidCnpIsNotAllowedTest() {
        MultipartFile file1 = mock(MultipartFile.class);
        MultipartFile file2 = mock(MultipartFile.class);

        IdCardExtractionResult result = mock(IdCardExtractionResult.class);

        when(ocrService.ocr(file1, file2)).thenReturn(result);
        when(identityCardUtil.validate(result)).thenReturn(false);
        when(identityCardProperties.isAllowInvalidCnp()).thenReturn(false);

        IllegalArgumentException actualException = assertThrows(
                IllegalArgumentException.class,
                () -> identityCardService.scanAndSave(file1, file2)
        );

        assertEquals("Received incorrect CNP", actualException.getMessage());
        verify(ocrService).ocr(file1, file2);
        verify(identityCardUtil).validate(result);
        verify(identityCardProperties).isAllowInvalidCnp();
        verifyNoInteractions(identityCardRepository, imageService, identityCardToResponseIdentityCard, extractionToIdentityCard);
    }

    @Test
    void shouldScanAndSaveWhenCnpIsInvalidButAllowedTest() {
        MultipartFile file1 = mock(MultipartFile.class);
        MultipartFile file2 = mock(MultipartFile.class);

        IdCardExtractionResult result = mock(IdCardExtractionResult.class);
        IdentityCard entity = new IdentityCard();
        IdentityCard savedId = new IdentityCard();
        ResponseIdentityCard expectedResponse = new ResponseIdentityCard();

        when(ocrService.ocr(file1, file2)).thenReturn(result);
        when(identityCardUtil.validate(result)).thenReturn(false);
        when(identityCardProperties.isAllowInvalidCnp()).thenReturn(true);
        when(extractionToIdentityCard.toEntity(result)).thenReturn(entity);
        when(identityCardRepository.save(entity)).thenReturn(savedId);
        when(identityCardToResponseIdentityCard.toEntity(entity)).thenReturn(expectedResponse);

        ResponseIdentityCard actualResponse = identityCardService.scanAndSave(file1, file2);

        assertSame(expectedResponse, actualResponse);
        verify(ocrService).ocr(file1, file2);
        verify(identityCardUtil).validate(result);
        verify(identityCardProperties).isAllowInvalidCnp();
        verify(extractionToIdentityCard).toEntity(result);
        verify(identityCardRepository).save(entity);
        verify(imageService).saveIdentityCardWithImage(savedId, file1);
        verify(identityCardToResponseIdentityCard).toEntity(entity);
    }

    @Test
    void shouldReturnPageWithResponseIdAndIdCardPropertiesTest() {
        String query = "andr";
        Pageable pageable = PageRequest.of(0, 10);

        IdentityCard identityCard = new IdentityCard();
        identityCard.setFirstName("Andrei");
        identityCard.setLastName("Popescu");
        identityCard.setCnp("1234567890123");

        Page<IdentityCard> page = new PageImpl<>(List.of(identityCard));

        ResponseIdentityCard expectedResponse = new ResponseIdentityCard();
        expectedResponse.setFirstName("Andrei");
        expectedResponse.setLastName("Popescu");
        expectedResponse.setCnp("1234567890123");

        when(identityCardRepository.findByCnpContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                query, query, query, pageable))
                .thenReturn(page);

        when(identityCardToResponseIdentityCard.toEntity(identityCard)).thenReturn(expectedResponse);

        Page<ResponseIdentityCard> actualResponse = identityCardService.search(query, pageable);

        assertEquals(1, actualResponse.getContent().size());
        assertSame(expectedResponse, actualResponse.getContent().getFirst());

        verify(identityCardRepository)
                .findByCnpContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                        query, query, query, pageable);
        verify(identityCardToResponseIdentityCard).toEntity(identityCard);
    }
}