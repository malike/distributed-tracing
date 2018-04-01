package st.malike.distributed.tracing.sleuth.app.malike.distributed.tracing.sleuth.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @autor malike_st
 */
@Service
public class DummyService {

  @Autowired
  RestTemplate restTemplate;
  @Value("${server.port:8080}")
  private int port;

  @NewSpan("getDummy()")
  public String getDummy() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    HttpEntity<?> entity = new HttpEntity<Object>(headers);
    ResponseEntity<String> response = restTemplate.exchange("http://localhost:" +
        port + "/api/another/dummy/" + 3, HttpMethod.GET, entity, String.class);
    return response.getBody();
  }
}
