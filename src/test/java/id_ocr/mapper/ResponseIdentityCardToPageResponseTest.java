package id_ocr.mapper;

import id_ocr.dto.PageResponse;
import id_ocr.dto.ResponseIdentityCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResponseIdentityCardToPageResponseTest {
    
    private ResponseIdentityCardToPageResponse responseIdentityCardToPageResponse;

    @BeforeEach
    void setUp() {
        responseIdentityCardToPageResponse = new ResponseIdentityCardToPageResponse();
    }

    @Test
    void shouldMapPageToPageResponseTest() {
        ResponseIdentityCard responseIdentityCard1 = new ResponseIdentityCard();
        responseIdentityCard1.setUid("UID1");

        ResponseIdentityCard responseIdentityCard2 = new ResponseIdentityCard();
        responseIdentityCard2.setUid("UID2");

        List<ResponseIdentityCard> content = List.of(responseIdentityCard1, responseIdentityCard2);

        Page<ResponseIdentityCard> page = new PageImpl<>(
                content,
                PageRequest.of(1, 2),
                5
        );

        PageResponse<ResponseIdentityCard> actualResult = responseIdentityCardToPageResponse.toEntity(page);

        assertEquals(content, actualResult.content());
        assertEquals(2, actualResult.totalElements());
        assertEquals(2, actualResult.size());
        assertEquals(1, actualResult.page());
        assertEquals(3, actualResult.totalPages());
    }
}