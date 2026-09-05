package id_ocr.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IdCardExtractionResultTest {

    @Test
    void shouldCreateIdExtractionResultRecordTest() {
        Fields fields = new Fields(
                "1234567890123",
                "Popescu",
                "Ion",
                "RX",
                "123456",
                "Bucharest",
                "M",
                "Romanian",
                "Bucharest",
                "01.01.2020",
                "01.01.2030",
                "SPCLEP",
                "10.05.1990"
        );

        Mrz mrz = new Mrz(
                "line1",
                "line2",
                "line3"
        );

        List<String> uncertainties = List.of("uncertainty1", "uncertainty2");

        IdCardExtractionResult result = new IdCardExtractionResult(
                "CI",
                fields,
                mrz,
                uncertainties
        );

        assertEquals("CI", result.cardModel());
        assertEquals(fields, result.fields());
        assertEquals(mrz, result.mrz());
        assertEquals(uncertainties, result.uncertainties());
    }

    @Test
    void shouldSupportEqualsHashCodeAndToStringTest() {
        Fields fields = new Fields(
                "1234567890123",
                "Popescu",
                "Ion",
                "RX",
                "123456",
                "Bucharest",
                "M",
                "Romanian",
                "Bucharest",
                "01.01.2020",
                "01.01.2030",
                "SPCLEP",
                "10.05.1990"
        );

        Mrz mrz = new Mrz(
                "line1",
                "line2",
                "line3"
        );

        List<String> uncertainties = List.of("uncertainty1", "uncertainty2");

        IdCardExtractionResult result1 = new IdCardExtractionResult(
                "CI",
                fields,
                mrz,
                uncertainties
        );

        IdCardExtractionResult result2 = new IdCardExtractionResult(
                "CI",
                fields,
                mrz,
                uncertainties
        );

        assertEquals(result1, result2);
        assertEquals(result1.hashCode(), result2.hashCode());
        assertNotNull(result1.toString());
    }
}