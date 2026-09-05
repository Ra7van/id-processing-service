package id_ocr;

import id_ocr.util.IdentityCardProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(IdentityCardProperties.class)
public class Application {

	static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
