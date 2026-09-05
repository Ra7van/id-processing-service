package id_ocr.controller;

import id_ocr.dto.PageResponse;
import id_ocr.dto.ResponseIdentityCard;
import id_ocr.mapper.ResponseIdentityCardToPageResponse;
import id_ocr.services.IdentityCardService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class OcrController {

    private final Logger log = LoggerFactory.getLogger(OcrController.class);
    private final IdentityCardService identityCardService;
    private final ResponseIdentityCardToPageResponse responseIdentityCardToPageResponse;

    public OcrController(IdentityCardService identityCardService, ResponseIdentityCardToPageResponse responseIdentityCardToPageResponse) {
        this.identityCardService = identityCardService;
        this.responseIdentityCardToPageResponse = responseIdentityCardToPageResponse;
    }

    @Operation(summary = "Scan identity card")
    @PostMapping(value = "/ic-reader", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseIdentityCard icReader(
            @RequestParam("file1") MultipartFile file1,
            @RequestParam(value = "file2", required = false) MultipartFile file2) {

        log.info("Received request for scanning identity card and converting it to JSON");

        return identityCardService.scanAndSave(file1, file2);
    }

    @GetMapping("/search")
    public PageResponse<ResponseIdentityCard> search(
            @RequestParam String query,
            @ParameterObject Pageable pageable) {

        log.info("Initiating search");

        return responseIdentityCardToPageResponse.toEntity(identityCardService.search(query, pageable));
    }
}