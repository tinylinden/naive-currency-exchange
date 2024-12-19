package eu.tinylinden.nce;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

import io.restassured.RestAssured;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(webEnvironment = DEFINED_PORT)
public abstract class BaseIT {

  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

  static {
    postgres.start();

    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    RestAssured.baseURI = "http://localhost:18080";
  }

  @DynamicPropertySource
  static void dynamicProps(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", () -> postgres.getJdbcUrl());
    registry.add("spring.datasource.username", () -> postgres.getUsername());
    registry.add("spring.datasource.password", () -> postgres.getPassword());

    // fixme: configure mock-server container and implement int. test for ExchangeCurrencyEndpoint
    registry.add("nce.adapters.nbp.url", () -> "http://localhost");
  }
}
