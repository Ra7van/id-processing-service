package id_ocr.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MetadatasResponse {
    private byte[] imageData;
    private String contentType;
    private String fileName;
    private Long fileSize;
    private LocalDateTime createdAt;
}
