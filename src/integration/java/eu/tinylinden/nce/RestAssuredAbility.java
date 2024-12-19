package eu.tinylinden.nce;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.MediaType;

public interface RestAssuredAbility {

  String DEFAULT_CUSTOMER_PASSWORD = "secret";

  default RequestSpecification request(String path) {
    return request(path, null);
  }

  default RequestSpecification request(String path, String body) {
    return request(path, body, null);
  }

  default RequestSpecification request(String path, String body, String customerNo) {
    var request =
        RestAssured.given()
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .basePath(path);

    if (customerNo != null) {
      request.auth().preemptive().basic(customerNo, DEFAULT_CUSTOMER_PASSWORD);
    }

    if (body != null) {
      request.body(body);
    }

    return request;
  }

  default String extractString(Response response, String path) {
    return response.body().jsonPath().getString(path);
  }
}
