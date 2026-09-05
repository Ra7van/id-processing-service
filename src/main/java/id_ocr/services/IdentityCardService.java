package id_ocr.services;

import id_ocr.dto.IdCardExtractionResult;
import id_ocr.dto.ResponseIdentityCard;
import id_ocr.entities.IdentityCard;
import id_ocr.mapper.ExtractionToIdentityCard;
import id_ocr.mapper.IdentityCardToResponseIdentityCard;
import id_ocr.repositories.IdentityCardRepository;
import id_ocr.util.IdentityCardProperties;
import id_ocr.util.IdentityCardUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class IdentityCardService {
    private final ImageService imageService;
    private final OcrService ocrService;
    private final IdentityCardRepository identityCardRepository;
    private final IdentityCardUtil identityCardUtil;
    private final ExtractionToIdentityCard extractiontoIdentityCard;
    private final IdentityCardToResponseIdentityCard identityCardToResponseIdentityCard;
    private final IdentityCardProperties identityCardProperties;

    private final Logger log = LoggerFactory.getLogger(IdentityCardService.class);

    public IdentityCardService(
            OcrService ocrService,
            IdentityCardRepository identityCardRepository,
            IdentityCardUtil identityCardUtil,
            ExtractionToIdentityCard extractiontoIdentityCard,
            IdentityCardToResponseIdentityCard identityCardToResponseIdentityCard,
            ImageService imageService,
            IdentityCardProperties identityCardProperties) {

        this.ocrService = ocrService;
        this.identityCardRepository = identityCardRepository;
        this.identityCardUtil = identityCardUtil;
        this.extractiontoIdentityCard = extractiontoIdentityCard;
        this.identityCardToResponseIdentityCard = identityCardToResponseIdentityCard;
        this.imageService = imageService;
        this.identityCardProperties = identityCardProperties;
    }

    public ResponseIdentityCard scanAndSave(MultipartFile file1, MultipartFile file2) {
        log.info("Scanning and verifying the given identity card");

        IdCardExtractionResult result = ocrService.ocr(file1, file2);

        if (!identityCardUtil.validate(result) && !identityCardProperties.isAllowInvalidCnp()) {
            log.warn("CNP is not valid");
            throw new IllegalArgumentException("Received incorrect CNP");
        }

        IdentityCard entity = extractiontoIdentityCard.toEntity(result);
        log.info("Received identity card is valid and will be saved");

        IdentityCard savedId =  identityCardRepository.save(entity);
        imageService.saveIdentityCardWithImage(savedId, file1);

        return identityCardToResponseIdentityCard.toEntity(entity);
    }

    public Page<ResponseIdentityCard> search(String query, Pageable pageable) {
        log.info("Searched for identity cards with query {}", query);

        return identityCardRepository
                .findByCnpContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                query, query, query, pageable)
                .map(identityCardToResponseIdentityCard::toEntity);
    }
}