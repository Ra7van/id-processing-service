package id_ocr.mapper;

import id_ocr.dto.ResponseIdentityCard;
import id_ocr.entities.IdentityCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IdentityCardToResponseIdentityCardTest {
    
    private IdentityCardToResponseIdentityCard identityCardToResponseIdentityCard;

    @BeforeEach
    void setUp() {
        identityCardToResponseIdentityCard = new IdentityCardToResponseIdentityCard();
    }

    @Test
    void shouldMapIdentityCardToResponseIdTest() {
        IdentityCard identityCard = new IdentityCard();
        identityCard.setUid("UID123");
        identityCard.setCardModel("CI");
        identityCard.setCnp("1234567890123");
        identityCard.setLastName("Popescu");
        identityCard.setFirstName("Ion");
        identityCard.setSeries("RX");
        identityCard.setNumber("123456");
        identityCard.setAddress("Bucharest");
        identityCard.setSex("M");
        identityCard.setCitizenship("Romanian");
        identityCard.setBirthPlace("Bucharest");
        identityCard.setValidFrom(LocalDate.of(2020, 1, 1));
        identityCard.setValidTo(LocalDate.of(2030, 1, 1));
        identityCard.setIssuedBy("SPCLEP");
        identityCard.setDateOfBirth(LocalDate.of(1990, 5, 10));
        identityCard.setMrzLine1("line1");
        identityCard.setMrzLine2("line2");
        identityCard.setMrzLine3("line3");

        ResponseIdentityCard actualResult = identityCardToResponseIdentityCard.toEntity(identityCard);

        assertEquals("UID123", actualResult.getUid());
        assertEquals("CI", actualResult.getCardModel());
        assertEquals("1234567890123", actualResult.getCnp());
        assertEquals("Popescu", actualResult.getLastName());
        assertEquals("Ion", actualResult.getFirstName());
        assertEquals("RX", actualResult.getSeries());
        assertEquals("123456", actualResult.getNumber());
        assertEquals("Bucharest", actualResult.getAddress());
        assertEquals("M", actualResult.getSex());
        assertEquals("Romanian", actualResult.getCitizenship());
        assertEquals("Bucharest", actualResult.getBirthPlace());
        assertEquals(LocalDate.of(2020, 1, 1), actualResult.getValidFrom());
        assertEquals(LocalDate.of(2030, 1, 1), actualResult.getValidTo());
        assertEquals("SPCLEP", actualResult.getIssuedBy());
        assertEquals(LocalDate.of(1990, 5, 10), actualResult.getDateOfBirth());
        assertEquals("line1", actualResult.getMrzLine1());
        assertEquals("line2", actualResult.getMrzLine2());
        assertEquals("line3", actualResult.getMrzLine3());
    }
}