package id_ocr.services;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import id_ocr.dto.CardModelResult;
import id_ocr.dto.IdCardExtractionResult;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Set;

import static org.springframework.http.HttpStatus.*;

@Service
public class OcrService {

    private static final long MAX_FILE_SIZE_IN_BYTES = 10 * 1024 * 1024;

    private static final Set<String> ALLOWED_FILE_TYPES = Set.of(
            "image/jpeg",
            "image/jpg",
            "image/png"
    );

    private final ChatClient chatClient;

    private final Logger log = LoggerFactory.getLogger(OcrService.class);

    @Value("classpath:/prompts/ocrSystemPrompt.st")
    private Resource systemPromptResource;

    @Value("classpath:/prompts/classifyCardModel.st")
    private Resource classifyPromptResource;

    @Value("classpath:/prompts/extractOldCard.st")
    private Resource extractOldPromptResource;

    @Value("classpath:/prompts/extractNewCard.st")
    private Resource extractNewPromptResource;

    private String classifyPrompt;
    private String extractOldPrompt;
    private String extractNewPrompt;
    private String systemPrompt;

    public OcrService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @PostConstruct
    void loadPrompts() throws IOException {
        systemPrompt = StreamUtils.copyToString(systemPromptResource.getInputStream(), StandardCharsets.UTF_8);
        classifyPrompt = StreamUtils.copyToString(classifyPromptResource.getInputStream(), StandardCharsets.UTF_8);
        extractOldPrompt = StreamUtils.copyToString(extractOldPromptResource.getInputStream(), StandardCharsets.UTF_8);
        extractNewPrompt = StreamUtils.copyToString(extractNewPromptResource.getInputStream(), StandardCharsets.UTF_8);
    }

    public IdCardExtractionResult ocr(MultipartFile file1, MultipartFile file2) {
        log.info("Trying to parse from image to JSON entity");

        validateRequiredImage(file1);
        validateImage(file1, "First file");

        if (hasFile(file2)) {
            validateImage(file2, "Second file");
        }

        try {
            MimeType mimeType1 = MimeTypeUtils.parseMimeType(Objects.requireNonNull(file1.getContentType()));
            MimeType mimeType2 = hasFile(file2)
                    ? MimeTypeUtils.parseMimeType(Objects.requireNonNull(file2.getContentType()))
                    : null;

            ByteArrayResource image1 = resource(file1);
            ByteArrayResource image2 = hasFile(file2) ? resource(file2) : null;

            String cardModel = classifyCardModel(mimeType1, image1, mimeType2, image2);
            String extractionPrompt = extractionPromptFor(cardModel);

            return extractCard(extractionPrompt, mimeType1, image1, mimeType2, image2);

        } catch (IOException e) {
            throw new ResponseStatusException(BAD_REQUEST, "Could not read file", e);
        }
    }

    private String classifyCardModel(
            MimeType mimeType1,
            ByteArrayResource image1,
            MimeType mimeType2,
            ByteArrayResource image2
    ) {
        CardModelResult result = chatClient.prompt()
                .system(systemPrompt)
                .user(user -> {
                    user.text(classifyPrompt);
                    user.media(mimeType1, image1);
                    if (mimeType2 != null && image2 != null) {
                        user.media(mimeType2, image2);
                    }
                })
                .call()
                .entity(CardModelResult.class);

        return requireCardModel(result);
    }

    private String requireCardModel(CardModelResult result) {
        String cardModel = result != null && result.cardModel() != null
                ? result.cardModel().trim().toLowerCase()
                : null;

        if (!"ci".equals(cardModel) && !"cei".equals(cardModel)) {
            throw new ResponseStatusException(
                    UNPROCESSABLE_CONTENT,
                    "Could not determine identity card model confidently"
            );
        }

        return cardModel;
    }

    private IdCardExtractionResult extractCard(
            String prompt,
            MimeType mimeType1,
            ByteArrayResource image1,
            MimeType mimeType2,
            ByteArrayResource image2
    ) {
        return chatClient.prompt()
                .system(systemPrompt)
                .user(user -> {
                    user.text(prompt);
                    user.media(mimeType1, image1);
                    if (mimeType2 != null && image2 != null) {
                        user.media(mimeType2, image2);
                    }
                })
                .call()
                .entity(IdCardExtractionResult.class);
    }

    private String extractionPromptFor(String cardModel) {
        return switch (cardModel) {
            case "ci" -> extractOldPrompt;
            case "cei" -> extractNewPrompt;
            default -> throw new ResponseStatusException(
                    UNPROCESSABLE_CONTENT,
                    "Could not determine identity card model confidently"
            );
        };
    }

    private void validateRequiredImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            log.warn("File is empty");
            throw new ResponseStatusException(BAD_REQUEST, "First file is required and must not be empty");
        }
    }

    private void validateImage(MultipartFile file, String label) {
        String contentType = file.getContentType();

        if (contentType == null || !ALLOWED_FILE_TYPES.contains(contentType.toLowerCase())) {
            throw new ResponseStatusException(
                    UNSUPPORTED_MEDIA_TYPE,
                    label + " must be a JPEG, JPG or PNG image"
            );
        }

        if (file.getSize() > MAX_FILE_SIZE_IN_BYTES) {
            log.warn("File is too large");

            throw new ResponseStatusException(
                    CONTENT_TOO_LARGE,
                    label + " exceeds the maximum allowed size of " + MAX_FILE_SIZE_IN_BYTES + " bytes"
            );
        }
    }

    private boolean hasFile(MultipartFile file) {
        return file != null && !file.isEmpty();
    }

    private ByteArrayResource resource(MultipartFile file) throws IOException {
        return new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };
    }
}