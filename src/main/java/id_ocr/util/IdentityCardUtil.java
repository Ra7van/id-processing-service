package id_ocr.util;

import id_ocr.dto.IdCardExtractionResult;
import org.springframework.stereotype.Component;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.time.LocalDate;

@Component
public class IdentityCardUtil {
    private static final String CNP_KEY = "279146358279";
    private final DateUtil formatter = new DateUtil();

    Logger log = LoggerFactory.getLogger(IdentityCardUtil.class);

    public boolean validate(IdCardExtractionResult result) {
        String cnp = result.fields().cnp();

        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int x = cnp.charAt(i) - '0';
            int y = CNP_KEY.charAt(i) - '0';
            sum += x * y;
        }

        int r = sum % 11;
        if (r == 10) {
            r = 1;
        }

        LocalDate now = LocalDate.now();
        LocalDate validFrom = formatter.parseData(result.fields().validFrom());
        LocalDate validTo = formatter.parseData(result.fields().validTo());

        if (r != cnp.charAt(12) - '0') {
            return false;
        }

        if (!validFrom.isBefore(now) || !validTo.isAfter(now)) {
            log.warn("Date is not valid");
            return false;
        }

        return true;
    }
}
