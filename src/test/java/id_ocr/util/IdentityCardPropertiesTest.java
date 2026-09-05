package id_ocr.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
public class IdentityCardPropertiesTest {
    @Test
    void shouldSetAllowInvalidCnpTest() {
        IdentityCardProperties properties = new IdentityCardProperties();

        properties.setAllowInvalidCnp(true);
        
        assertTrue(properties.isAllowInvalidCnp());
    }
}
