package eu.tinylinden.nce.customers.adapters.rest.v1;

import eu.tinylinden.nce.BaseIT;
import org.junit.jupiter.api.Test;

class RegisterCustomerEndpointIT extends BaseIT implements CustomersApiAbility {

  @Test
  void shouldRegisterCustomer() {
    // when
    var response = request("/api/v1/customers", registerCustomerRequest()).post();

    // then
    response.then().statusCode(200);
  }
}
