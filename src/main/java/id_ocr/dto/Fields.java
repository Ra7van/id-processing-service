package id_ocr.dto;

public record Fields(
        String cnp,
        String lastName,
        String firstName,
        String series,
        String number,
        String address,
        String sex,
        String citizenship,
        String birthPlace,
        String validFrom,
        String validTo,
        String issuedBy,
        String dateOfBirth
) {
}