package id_ocr.mapper;

import id_ocr.dto.Fields;
import id_ocr.dto.IdCardExtractionResult;
import id_ocr.dto.Mrz;
import id_ocr.entities.IdentityCard;
import id_ocr.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ExtractionToIdentityCardTest {

    private DateUtil formatter;
    private ExtractionToIdentityCard extractionToIdentityCard;

    @BeforeEach
    void setUp() {
        formatter = mock(DateUtil.class);
        extractionToIdentityCard = new ExtractionToIdentityCard(formatter);
    }

    @Test
    void shouldMapExtractionResultToIdentityCardTest() {
        IdCardExtractionResult result = mock(IdCardExtractionResult.class);
        Fields fields = mock(Fields.class);
        Mrz mrz = mock(Mrz.class);

        LocalDate validFrom = LocalDate.of(2020, 1, 1);
        LocalDate validTo = LocalDate.of(2030, 1, 1);
        LocalDate dateOfBirth = LocalDate.of(1990, 5, 10);

        when(result.cardModel()).thenReturn("CI");
        when(result.fields()).thenReturn(fields);
        when(result.mrz()).thenReturn(mrz);
        when(fields.cnp()).thenReturn("1234567890123");
        when(fields.lastName()).thenReturn("Popescu");
        when(fields.firstName()).thenReturn("Ion");
        when(fields.series()).thenReturn("RX");
        when(fields.number()).thenReturn("123456");
        when(fields.address()).thenReturn("Bucharest");
        when(fields.sex()).thenReturn("M");
        when(fields.citizenship()).thenReturn("Romanian");
        when(fields.birthPlace()).thenReturn("Bucharest");
        when(fields.validFrom()).thenReturn("01.01.2020");
        when(fields.validTo()).thenReturn("01.01.2030");
        when(fields.issuedBy()).thenReturn("SPCLEP");
        when(fields.dateOfBirth()).thenReturn("10.05.1990");
        when(mrz.line1()).thenReturn("line1");
        when(mrz.line2()).thenReturn("line2");
        when(mrz.line3()).thenReturn("line3");
        when(formatter.parseData("01.01.2020")).thenReturn(validFrom);
        when(formatter.parseData("01.01.2030")).thenReturn(validTo);
        when(formatter.parseData("10.05.1990")).thenReturn(dateOfBirth);

        IdentityCard actualResult = extractionToIdentityCard.toEntity(result);

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
        assertEquals(validFrom, actualResult.getValidFrom());
        assertEquals(validTo, actualResult.getValidTo());
        assertEquals("SPCLEP", actualResult.getIssuedBy());
        assertEquals(dateOfBirth, actualResult.getDateOfBirth());
        assertEquals("line1", actualResult.getMrzLine1());
        assertEquals("line2", actualResult.getMrzLine2());
        assertEquals("line3", actualResult.getMrzLine3());

        verify(formatter).parseData("01.01.2020");
        verify(formatter).parseData("01.01.2030");
        verify(formatter).parseData("10.05.1990");
    }
}