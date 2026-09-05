package id_ocr.mapper;

import id_ocr.dto.PageResponse;
import id_ocr.dto.ResponseIdentityCard;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class ResponseIdentityCardToPageResponse {

    public PageResponse<ResponseIdentityCard> toEntity(Page<ResponseIdentityCard> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumberOfElements(),
                page.getSize(),
                page.getNumber(),
                page.getTotalPages()
        );
    }
}
