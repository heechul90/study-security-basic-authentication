package study.security.basicauthentication;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BasicAuthenticationApplicationTests {

    @LocalServerPort
    int port;

    RestTemplate client = new RestTemplate();

    private String greetingUrl() {
        return "http://localhost:" + port + "/greeting";
    }

    @Test
    @DisplayName("인증 실패")
    void authenticated_fail() {
        //String response = client.getForObject(greetingUrl(), String.class);
        assertThatThrownBy(() -> client.getForObject(greetingUrl(), String.class))
                .isInstanceOf(HttpClientErrorException.class);
    }

    @Test
    @DisplayName("인증 성공1")
    void authenticated_success1() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Basic " + Base64.getEncoder().encodeToString(
                "user:1234".getBytes()
        ));
        HttpEntity entity = new HttpEntity(null, headers);
        ResponseEntity<String> response = client.exchange(greetingUrl(), HttpMethod.GET, entity, String.class);

        assertThat(response.getBody()).isEqualTo("hello");
    }

    @Test
    @DisplayName("인증 성공2")
    void authenticated_success2() {
        TestRestTemplate testClient = new TestRestTemplate("user", "1234");
        String response = testClient.getForObject(greetingUrl(), String.class);
        assertThat(response).isEqualTo("hello");
    }

    @Test
    @DisplayName("POST 인증")
    void authenticated_success3_post() {
        TestRestTemplate testClient = new TestRestTemplate("user", "1234");
        ResponseEntity<String> response = testClient.postForEntity(greetingUrl(), "heech", String.class);
        assertThat(response.getBody()).isEqualTo("hello heech");
    }
}
