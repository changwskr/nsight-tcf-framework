package nhnis.eos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(scanBasePackages = "nhnis")
@ConfigurationPropertiesScan("nhnis")
public class PdmgEosApplication {

    public static void main(String[] args) {
        SpringApplication.run(PdmgEosApplication.class, args);
    }
}
