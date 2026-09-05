package id_ocr.dto;

import java.util.List;

public record PageResponse<T>(
        List<T> content,
        int totalElements,
        int size,
        int page,
        int totalPages
) {
}