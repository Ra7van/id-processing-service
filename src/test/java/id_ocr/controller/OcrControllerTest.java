package id_ocr.controller;

import id_ocr.dto.PageResponse;
import id_ocr.dto.ResponseIdentityCard;
import id_ocr.mapper.ResponseIdentityCardToPageResponse;
import id_ocr.services.IdentityCardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OcrControllerTest {

    @Mock
    private IdentityCardService identityCardService;

    @Mock
    private ResponseIdentityCardToPageResponse responseIdentityCardToPageResponse;

    @InjectMocks
    private OcrController ocrController;

    @Test
    void shouldCallWithBothFilesHappyTest() {
        MockMultipartFile file1 = new MockMultipartFile(
                "file",
                "front.png",
                "image/png",
                "front-content".getBytes()
        );

        MockMultipartFile file2 = new MockMultipartFile(
                "file2",
                "back.png",
                "image/png",
                "back-content".getBytes()
        );

        ResponseIdentityCard expectedResult = new ResponseIdentityCard();
        expectedResult.setUid("0c9778cc");
        expectedResult.setCnp("9999999999999");
        expectedResult.setFirstName("Maria");
        expectedResult.setLastName("Ionescu");

        when(identityCardService.scanAndSave(file1, file2)).thenReturn(expectedResult);

        ResponseIdentityCard actualResult = ocrController.icReader(file1, file2);

        assertSame(expectedResult, actualResult);
        verify(identityCardService).scanAndSave(file1, file2);
    }

    @Test
    void shouldCallWithFirstFileHappyTest() {
        MockMultipartFile file1 = new MockMultipartFile(
                "file1",
                "front.png",
                "image/png",
                "front-content".getBytes()
        );

        ResponseIdentityCard expectedResult = new ResponseIdentityCard();
        expectedResult.setUid("0c9778cc");
        expectedResult.setCnp("9999999999999");
        expectedResult.setFirstName("Maria");
        expectedResult.setLastName("Ionescu");

        when(identityCardService.scanAndSave(file1, null)).thenReturn(expectedResult);

        ResponseIdentityCard actualResult = ocrController.icReader(file1, null);
        assertSame(expectedResult, actualResult);
        verify(identityCardService).scanAndSave(file1, null);
    }

    @Test
    void shouldSearchHappyTest() {
        String query = "Andr";
        Pageable pageable = PageRequest.of(0, 10);

        ResponseIdentityCard responseIdentityCard = new ResponseIdentityCard();
        responseIdentityCard.setUid("0c9778cc");
        responseIdentityCard.setCnp("9999999999999");
        responseIdentityCard.setFirstName("Maria");
        responseIdentityCard.setLastName("Ionescu");

        Page<ResponseIdentityCard> result = new PageImpl<>(List.of(responseIdentityCard));
        PageResponse<ResponseIdentityCard> expectedResult = mock(PageResponse.class);

        when(identityCardService.search(query, pageable)).thenReturn(result);
        when(responseIdentityCardToPageResponse.toEntity(result)).thenReturn(expectedResult);

        PageResponse<ResponseIdentityCard> actualResult = ocrController.search(query, pageable);

        assertSame(expectedResult, actualResult);
        verify(identityCardService).search(query, pageable);
        verify(responseIdentityCardToPageResponse).toEntity(result);
    }
}