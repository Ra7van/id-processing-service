package id_ocr.services;

import id_ocr.dto.CardModelResult;
import id_ocr.dto.IdCardExtractionResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class OcrServiceTest {

    @Mock
    private ChatClient chatClient;

    @Mock
    private ChatClient.PromptUserSpec promptUserSpec;

    @Mock
    private ChatClient.ChatClientRequestSpec requestSpec1;

    @Mock
    private ChatClient.ChatClientRequestSpec requestSpec2;

    @Mock
    private ChatClient.CallResponseSpec responseSpec1;

    @Mock
    private ChatClient.CallResponseSpec responseSpec2;

    @Mock
    private Resource systemPromptResource;

    @Mock
    private Resource classifyPromptResource;

    @Mock
    private Resource extractOldPromptResource;

    @Mock
    private Resource extractNewPromptResource;

    @InjectMocks
    private OcrService ocrService;

    @BeforeEach
    void setUp() {
        ocrService = new OcrService(chatClient);

        ReflectionTestUtils.setField(ocrService, "systemPrompt", "system-prompt");
        ReflectionTestUtils.setField(ocrService, "classifyPrompt", "classify-prompt");
        ReflectionTestUtils.setField(ocrService, "extractOldPrompt", "extract-old-prompt");
        ReflectionTestUtils.setField(ocrService, "extractNewPrompt", "extract-new-prompt");
    }

    @Test
    void shouldLoadPrompts() throws Exception {
        ReflectionTestUtils.setField(ocrService, "systemPromptResource", systemPromptResource);
        ReflectionTestUtils.setField(ocrService, "classifyPromptResource", classifyPromptResource);
        ReflectionTestUtils.setField(ocrService, "extractOldPromptResource", extractOldPromptResource);
        ReflectionTestUtils.setField(ocrService, "extractNewPromptResource", extractNewPromptResource);

        when(systemPromptResource.getInputStream())
                .thenReturn(new ByteArrayInputStream("system".getBytes(StandardCharsets.UTF_8)));
        when(classifyPromptResource.getInputStream())
                .thenReturn(new ByteArrayInputStream("classify".getBytes(StandardCharsets.UTF_8)));
        when(extractOldPromptResource.getInputStream())
                .thenReturn(new ByteArrayInputStream("old".getBytes(StandardCharsets.UTF_8)));
        when(extractNewPromptResource.getInputStream())
                .thenReturn(new ByteArrayInputStream("new".getBytes(StandardCharsets.UTF_8)));

        ocrService.loadPrompts();

        assertEquals("system", ReflectionTestUtils.getField(ocrService, "systemPrompt"));
        assertEquals("classify", ReflectionTestUtils.getField(ocrService, "classifyPrompt"));
        assertEquals("old", ReflectionTestUtils.getField(ocrService, "extractOldPrompt"));
        assertEquals("new", ReflectionTestUtils.getField(ocrService, "extractNewPrompt"));
    }

    @Test
    void shouldThrowBadRequestWhenFile1IsNull() {
        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> ocrService.ocr(null, null)
        );

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertEquals("First file is required and must not be empty", ex.getReason());
    }

    @Test
    void shouldThrowBadRequestWhenFile1IsEmpty() {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "empty.png",
                "image/png",
                new byte[0]
        );

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> ocrService.ocr(file, null)
        );

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertEquals("First file is required and must not be empty", ex.getReason());
    }

    @Test
    void shouldThrowBadRequestWhenFile1ContentTypeIsNull() {
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(file.getContentType()).thenReturn(null);

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> ocrService.ocr(file, null)
        );

        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getStatusCode());
        assertEquals("First file must be a JPEG, JPG or PNG image", ex.getReason());
    }

    @Test
    void shouldThrowBadRequestWhenFile1InvalidTypeTest() {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.pdf",
                "application/pdf",
                new byte[100]
        );

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> ocrService.ocr(file, null)
        );

        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getStatusCode());
        assertEquals("First file must be a JPEG, JPG or PNG image", ex.getReason());
    }

    @Test
    void shouldThrowContentTooLargeWhenFile1LimitExceededTest() {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.png",
                "image/png",
                new byte[10 * 1024 * 1024 + 1]
        );

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> ocrService.ocr(file, null)
        );

        assertEquals(HttpStatus.CONTENT_TOO_LARGE, ex.getStatusCode());
        assertEquals(
                "First file exceeds the maximum allowed size of 10485760 bytes",
                ex.getReason()
        );
    }

    @Test
    void shouldThrowBadRequestWhenFile2InvalidTypeTest() {
        MockMultipartFile file1 = new MockMultipartFile(
                "file1",
                "test.png",
                "image/png",
                new byte[100]
        );

        MockMultipartFile file2 = new MockMultipartFile(
                "file2",
                "test.pdf",
                "application/pdf",
                new byte[100]
        );

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> ocrService.ocr(file1, file2)
        );

        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getStatusCode());
        assertEquals("Second file must be a JPEG, JPG or PNG image", ex.getReason());
    }

    @Test
    void shouldThrowContentTooLargeWhenFile2LimitExceededTest() {
        MockMultipartFile file1 = new MockMultipartFile(
                "file1",
                "test.png",
                "image/png",
                new byte[100]
        );

        MockMultipartFile file2 = new MockMultipartFile(
                "file2",
                "test2.png",
                "image/png",
                new byte[10 * 1024 * 1024 + 1]
        );

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> ocrService.ocr(file1, file2)
        );

        assertEquals(HttpStatus.CONTENT_TOO_LARGE, ex.getStatusCode());
        assertEquals(
                "Second file exceeds the maximum allowed size of 10485760 bytes",
                ex.getReason()
        );
    }

    @Test
    void shouldThrowBadRequestWhenFileCannotBeRead() throws Exception {
        MultipartFile file = mock(MultipartFile.class);

        when(file.isEmpty()).thenReturn(false);
        when(file.getContentType()).thenReturn("image/png");
        when(file.getSize()).thenReturn(100L);
        when(file.getBytes()).thenThrow(new IOException("read error"));

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> ocrService.ocr(file, null)
        );

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertEquals("Could not read file", ex.getReason());
    }

    @Test
    void shouldExtractOldCardFromSingleValidImage() throws Exception {
        MultipartFile file1 = mock(MultipartFile.class);

        when(file1.isEmpty()).thenReturn(false);
        when(file1.getContentType()).thenReturn("image/png");
        when(file1.getSize()).thenReturn(1024L);
        when(file1.getBytes()).thenReturn("image-bytes".getBytes());

        CardModelResult cardModelResult = new CardModelResult("CI");
        IdCardExtractionResult expectedResult = mock(IdCardExtractionResult.class);

        when(chatClient.prompt()).thenReturn(requestSpec1, requestSpec2);

        when(requestSpec1.system(anyString())).thenReturn(requestSpec1);
        when(requestSpec1.user(any(Consumer.class))).thenReturn(requestSpec1);
        when(requestSpec1.call()).thenReturn(responseSpec1);
        when(responseSpec1.entity(CardModelResult.class)).thenReturn(cardModelResult);

        when(requestSpec2.system(anyString())).thenReturn(requestSpec2);
        when(requestSpec2.user(any(Consumer.class))).thenReturn(requestSpec2);
        when(requestSpec2.call()).thenReturn(responseSpec2);
        when(responseSpec2.entity(IdCardExtractionResult.class)).thenReturn(expectedResult);

        IdCardExtractionResult actualResponse = ocrService.ocr(file1, null);

        assertSame(expectedResult, actualResponse);

        verify(chatClient, times(2)).prompt();
        verify(requestSpec1).system("system-prompt");
        verify(requestSpec2).system("system-prompt");
        verify(responseSpec1).entity(CardModelResult.class);
        verify(responseSpec2).entity(IdCardExtractionResult.class);
    }

    @Test
    void shouldExtractCardWithTwoImagesTest() {
        ByteArrayResource image1 = new ByteArrayResource("image1".getBytes());
        ByteArrayResource image2 = new ByteArrayResource("image2".getBytes());

        MimeType mimeType1 = MimeTypeUtils.parseMimeType("image/png");
        MimeType mimeType2 = MimeTypeUtils.parseMimeType("image/jpeg");

        IdCardExtractionResult expectedResult = mock(IdCardExtractionResult.class);

        when(chatClient.prompt()).thenReturn(requestSpec1);
        when(requestSpec1.system("system-prompt")).thenReturn(requestSpec1);

        when(promptUserSpec.text("extract-new-prompt")).thenReturn(promptUserSpec);
        when(promptUserSpec.media(mimeType1, image1)).thenReturn(promptUserSpec);
        when(promptUserSpec.media(mimeType2, image2)).thenReturn(promptUserSpec);

        when(requestSpec1.user(any(Consumer.class))).thenAnswer(invocation -> {
            Consumer<ChatClient.PromptUserSpec> consumer = invocation.getArgument(0);
            consumer.accept(promptUserSpec);
            return requestSpec1;
        });

        when(requestSpec1.call()).thenReturn(responseSpec1);
        when(responseSpec1.entity(IdCardExtractionResult.class)).thenReturn(expectedResult);

        IdCardExtractionResult actualResult = ReflectionTestUtils.invokeMethod(
                ocrService,
                "extractCard",
                "extract-new-prompt",
                mimeType1,
                image1,
                mimeType2,
                image2
        );

        assertSame(expectedResult, actualResult);
        verify(promptUserSpec).text("extract-new-prompt");
        verify(promptUserSpec).media(mimeType1, image1);
        verify(promptUserSpec).media(mimeType2, image2);
        verify(responseSpec1).entity(IdCardExtractionResult.class);
    }

    @Test
    void shouldExtractNewCardFromSingleValidImage() throws Exception {
        MultipartFile file1 = mock(MultipartFile.class);

        when(file1.isEmpty()).thenReturn(false);
        when(file1.getContentType()).thenReturn("image/png");
        when(file1.getSize()).thenReturn(1024L);
        when(file1.getBytes()).thenReturn("image-bytes".getBytes());

        CardModelResult cardModelResult = new CardModelResult("CEI");
        IdCardExtractionResult expectedResult = mock(IdCardExtractionResult.class);

        when(chatClient.prompt()).thenReturn(requestSpec1, requestSpec2);

        when(requestSpec1.system(anyString())).thenReturn(requestSpec1);
        when(requestSpec1.user(any(Consumer.class))).thenReturn(requestSpec1);
        when(requestSpec1.call()).thenReturn(responseSpec1);
        when(responseSpec1.entity(CardModelResult.class)).thenReturn(cardModelResult);

        when(requestSpec2.system(anyString())).thenReturn(requestSpec2);
        when(requestSpec2.user(any(Consumer.class))).thenReturn(requestSpec2);
        when(requestSpec2.call()).thenReturn(responseSpec2);
        when(responseSpec2.entity(IdCardExtractionResult.class)).thenReturn(expectedResult);

        IdCardExtractionResult actualResponse = ocrService.ocr(file1, null);

        assertSame(expectedResult, actualResponse);

        verify(chatClient, times(2)).prompt();
        verify(requestSpec1).system("system-prompt");
        verify(requestSpec2).system("system-prompt");
        verify(responseSpec1).entity(CardModelResult.class);
        verify(responseSpec2).entity(IdCardExtractionResult.class);
    }

    @Test
    void shouldExtractCardWithSingleImageTest() {
        ByteArrayResource image1 = new ByteArrayResource("image1".getBytes());
        MimeType mimeType1 = MimeTypeUtils.parseMimeType("image/png");
        IdCardExtractionResult expectedResult = mock(IdCardExtractionResult.class);

        when(chatClient.prompt()).thenReturn(requestSpec1);
        when(requestSpec1.system("system-prompt")).thenReturn(requestSpec1);

        when(promptUserSpec.text("extract-old-prompt")).thenReturn(promptUserSpec);
        when(promptUserSpec.media(any(MimeType.class), any(ByteArrayResource.class))).thenReturn(promptUserSpec);

        when(requestSpec1.user(any(Consumer.class))).thenAnswer(invocation -> {
            Consumer<ChatClient.PromptUserSpec> consumer = invocation.getArgument(0);
            consumer.accept(promptUserSpec);
            return requestSpec1;
        });

        when(requestSpec1.call()).thenReturn(responseSpec1);
        when(responseSpec1.entity(IdCardExtractionResult.class)).thenReturn(expectedResult);

        IdCardExtractionResult actualResult = ReflectionTestUtils.invokeMethod(
                ocrService,
                "extractCard",
                "extract-old-prompt",
                mimeType1,
                image1,
                null,
                null
        );

        assertSame(expectedResult, actualResult);
        verify(requestSpec1).system("system-prompt");
        verify(promptUserSpec).text("extract-old-prompt");
        verify(promptUserSpec).media(eq(mimeType1), any(ByteArrayResource.class));
        verify(responseSpec1).entity(IdCardExtractionResult.class);
    }

    @Test
    void shouldExtractOldCardFromTwoValidImages() throws Exception {
        MultipartFile file1 = mock(MultipartFile.class);
        MultipartFile file2 = mock(MultipartFile.class);

        when(file1.isEmpty()).thenReturn(false);
        when(file1.getContentType()).thenReturn("image/png");
        when(file1.getSize()).thenReturn(1024L);
        when(file1.getBytes()).thenReturn("image1-bytes".getBytes());

        when(file2.isEmpty()).thenReturn(false);
        when(file2.getContentType()).thenReturn("image/jpeg");
        when(file2.getSize()).thenReturn(2048L);
        when(file2.getBytes()).thenReturn("image2-bytes".getBytes());

        CardModelResult cardModelResult = new CardModelResult("CI");
        IdCardExtractionResult expectedResult = mock(IdCardExtractionResult.class);

        when(chatClient.prompt()).thenReturn(requestSpec1, requestSpec2);

        when(requestSpec1.system(anyString())).thenReturn(requestSpec1);
        when(requestSpec1.user(any(Consumer.class))).thenReturn(requestSpec1);
        when(requestSpec1.call()).thenReturn(responseSpec1);
        when(responseSpec1.entity(CardModelResult.class)).thenReturn(cardModelResult);

        when(requestSpec2.system(anyString())).thenReturn(requestSpec2);
        when(requestSpec2.user(any(Consumer.class))).thenReturn(requestSpec2);
        when(requestSpec2.call()).thenReturn(responseSpec2);
        when(responseSpec2.entity(IdCardExtractionResult.class)).thenReturn(expectedResult);

        IdCardExtractionResult actualResponse = ocrService.ocr(file1, file2);

        assertSame(expectedResult, actualResponse);

        verify(chatClient, times(2)).prompt();
        verify(responseSpec1).entity(CardModelResult.class);
        verify(responseSpec2).entity(IdCardExtractionResult.class);
    }

    @Test
    void shouldExtractNewCardFromTwoValidImages() throws Exception {
        MultipartFile file1 = mock(MultipartFile.class);
        MultipartFile file2 = mock(MultipartFile.class);

        when(file1.isEmpty()).thenReturn(false);
        when(file1.getContentType()).thenReturn("image/png");
        when(file1.getSize()).thenReturn(1024L);
        when(file1.getBytes()).thenReturn("image1-bytes".getBytes());

        when(file2.isEmpty()).thenReturn(false);
        when(file2.getContentType()).thenReturn("image/jpeg");
        when(file2.getSize()).thenReturn(2048L);
        when(file2.getBytes()).thenReturn("image2-bytes".getBytes());

        CardModelResult cardModelResult = new CardModelResult("CEI");
        IdCardExtractionResult expectedResult = mock(IdCardExtractionResult.class);

        when(chatClient.prompt()).thenReturn(requestSpec1, requestSpec2);

        when(requestSpec1.system(anyString())).thenReturn(requestSpec1);
        when(requestSpec1.user(any(Consumer.class))).thenReturn(requestSpec1);
        when(requestSpec1.call()).thenReturn(responseSpec1);
        when(responseSpec1.entity(CardModelResult.class)).thenReturn(cardModelResult);

        when(requestSpec2.system(anyString())).thenReturn(requestSpec2);
        when(requestSpec2.user(any(Consumer.class))).thenReturn(requestSpec2);
        when(requestSpec2.call()).thenReturn(responseSpec2);
        when(responseSpec2.entity(IdCardExtractionResult.class)).thenReturn(expectedResult);

        IdCardExtractionResult actualResponse = ocrService.ocr(file1, file2);

        assertSame(expectedResult, actualResponse);

        verify(chatClient, times(2)).prompt();
        verify(responseSpec1).entity(CardModelResult.class);
        verify(responseSpec2).entity(IdCardExtractionResult.class);
    }

    @Test
    void shouldThrowUnprocessableContentWhenClassificationReturnsNull() throws Exception {
        MultipartFile file1 = mock(MultipartFile.class);

        when(file1.isEmpty()).thenReturn(false);
        when(file1.getContentType()).thenReturn("image/png");
        when(file1.getSize()).thenReturn(1024L);
        when(file1.getBytes()).thenReturn("image-bytes".getBytes());

        when(chatClient.prompt()).thenReturn(requestSpec1);

        when(requestSpec1.system(anyString())).thenReturn(requestSpec1);
        when(requestSpec1.user(any(Consumer.class))).thenReturn(requestSpec1);
        when(requestSpec1.call()).thenReturn(responseSpec1);
        when(responseSpec1.entity(CardModelResult.class)).thenReturn(null);

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> ocrService.ocr(file1, null)
        );

        assertEquals(HttpStatus.UNPROCESSABLE_CONTENT, ex.getStatusCode());
        assertEquals("Could not determine identity card model confidently", ex.getReason());

        verify(chatClient, times(1)).prompt();
        verify(responseSpec1).entity(CardModelResult.class);
    }

    @Test
    void shouldThrowUnprocessableContentWhenClassificationReturnsInvalidValue() throws Exception {
        MultipartFile file1 = mock(MultipartFile.class);

        when(file1.isEmpty()).thenReturn(false);
        when(file1.getContentType()).thenReturn("image/png");
        when(file1.getSize()).thenReturn(1024L);
        when(file1.getBytes()).thenReturn("image-bytes".getBytes());

        CardModelResult cardModelResult = new CardModelResult("unknown");

        when(chatClient.prompt()).thenReturn(requestSpec1);

        when(requestSpec1.system(anyString())).thenReturn(requestSpec1);
        when(requestSpec1.user(any(Consumer.class))).thenReturn(requestSpec1);
        when(requestSpec1.call()).thenReturn(responseSpec1);
        when(responseSpec1.entity(CardModelResult.class)).thenReturn(cardModelResult);

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> ocrService.ocr(file1, null)
        );

        assertEquals(HttpStatus.UNPROCESSABLE_CONTENT, ex.getStatusCode());
        assertEquals("Could not determine identity card model confidently", ex.getReason());

        verify(chatClient, times(1)).prompt();
        verify(responseSpec1).entity(CardModelResult.class);
    }

    @Test
    void shouldThrowBadRequestWhenSecondFileCannotBeRead() throws Exception {
        MultipartFile file1 = mock(MultipartFile.class);
        MultipartFile file2 = mock(MultipartFile.class);

        when(file1.isEmpty()).thenReturn(false);
        when(file1.getContentType()).thenReturn("image/png");
        when(file1.getSize()).thenReturn(1024L);
        when(file1.getBytes()).thenReturn("image1-bytes".getBytes());

        when(file2.isEmpty()).thenReturn(false);
        when(file2.getContentType()).thenReturn("image/jpeg");
        when(file2.getSize()).thenReturn(2048L);
        when(file2.getBytes()).thenThrow(new IOException("read error"));

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> ocrService.ocr(file1, file2)
        );

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertEquals("Could not read file", ex.getReason());
    }

    @Test
    void shouldThrowIOExceptionWhenPromptLoadingFailsTest() throws Exception {
        ReflectionTestUtils.setField(ocrService, "systemPromptResource", systemPromptResource);

        when(systemPromptResource.getInputStream()).thenThrow(new IOException("read failed"));

        assertThrows(IOException.class, () -> ocrService.loadPrompts());
    }

    @Test
    void shouldReturnFalseWhenHasFileReceivesEmptyFileTest() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);

        Boolean actualResult = ReflectionTestUtils.invokeMethod(ocrService, "hasFile", file);

        assertNotEquals(Boolean.TRUE, actualResult);
    }

    @Test
    void shouldReturnTrueWhenHasFileReceivesNonEmptyFileTest() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);

        Boolean actualResult = ReflectionTestUtils.invokeMethod(ocrService, "hasFile", file);

        assertEquals(Boolean.TRUE, actualResult);
    }

    @Test
    void shouldCreateResourceWithOriginalFilenameTest() throws Exception {
        MultipartFile file = mock(MultipartFile.class);

        byte[] content = "image-bytes".getBytes();

        when(file.getBytes()).thenReturn(content);
        when(file.getOriginalFilename()).thenReturn("front.png");

        ByteArrayResource actualResource =
                ReflectionTestUtils.invokeMethod(ocrService, "resource", file);

        assertNotNull(actualResource);
        assertArrayEquals(content, actualResource.getByteArray());
        assertEquals("front.png", actualResource.getFilename());
    }
}