package id_ocr.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DateUtilTest {

    private DateUtil dateUtil;

    @BeforeEach
    void setUp() {
        dateUtil = new DateUtil();
    }

    @Test
    void shouldReturnNullWhenValueIsNullTest() {
        LocalDate actualResult = dateUtil.parseData(null);

        assertNull(actualResult);
    }

    @Test
    void shouldParseLongDateFormatTest() {
        LocalDate actualResult = dateUtil.parseData("25.12.2024");
        
        assertEquals(LocalDate.of(2024, 12, 25), actualResult);
    }

    @Test
    void shouldParseShortDateFormatTest() {
        LocalDate actualResult = dateUtil.parseData("25.12.24");

        assertEquals(LocalDate.of(2024, 12, 25), actualResult);
    }

    @Test
    void shouldTrimAndParseDateTest() {
        LocalDate actualResult = dateUtil.parseData(" 25.12.2024 ");

        assertEquals(LocalDate.of(2024, 12, 25), actualResult);
    }

    @Test
    void shouldThrowExceptionWhenDateFormatIsInvalidTest() {
        assertThrows(
                DateTimeParseException.class,
                () -> dateUtil.parseData("2024-12-25")
        );
    }
}
