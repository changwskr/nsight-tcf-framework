package nhnis.ontology.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HealthControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void health_returnsUp() {
        ResponseEntity<String> response = restTemplate.getForEntity("/health", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("tcf-ontology-service");
        assertThat(response.getBody()).contains("UP");
    }
}
