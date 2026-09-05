package id_ocr.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class DateUtil {

    public LocalDate parseData(String value) {
        if (value == null) {
            return null;
        }

        value = value.trim();

        DateTimeFormatter shortFormatter = DateTimeFormatter.ofPattern("dd.MM.yy");
        DateTimeFormatter longFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        try {
            return LocalDate.parse(value, longFormatter);
        } catch (DateTimeParseException e) {
            return LocalDate.parse(value, shortFormatter);
        }
    }
}
