package id_ocr.dto;

import java.util.List;

public record IdCardExtractionResult(
        String cardModel,
        Fields fields,
        Mrz mrz,
        List<String> uncertainties
) {
}