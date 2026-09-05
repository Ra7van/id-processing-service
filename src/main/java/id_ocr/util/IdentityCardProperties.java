package id_ocr.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.identity-card")
public class IdentityCardProperties {
    private boolean allowInvalidCnp;
}
