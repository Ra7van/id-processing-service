package id_ocr.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldsTest {
    
    @Test
    void shouldCreateFieldsRecordTest() {
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

        assertEquals("1234567890123", fields.cnp());
        assertEquals("Popescu", fields.lastName());
        assertEquals("Ion", fields.firstName());
        assertEquals("RX", fields.series());
        assertEquals("123456", fields.number());
        assertEquals("Bucharest", fields.address());
        assertEquals("M", fields.sex());
        assertEquals("Romanian", fields.citizenship());
        assertEquals("Bucharest", fields.birthPlace());
        assertEquals("01.01.2020", fields.validFrom());
        assertEquals("01.01.2030", fields.validTo());
        assertEquals("SPCLEP", fields.issuedBy());
        assertEquals("10.05.1990", fields.dateOfBirth());
    }
}