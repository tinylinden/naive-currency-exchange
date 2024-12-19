package eu.tinylinden.nce.wallets.adapters.rest.v1;

import eu.tinylinden.nce.BaseIT;
import eu.tinylinden.nce.customers.adapters.rest.v1.CustomersApiAbility;
import org.junit.jupiter.api.Test;

class ListWalletsEndpointIT extends BaseIT implements CustomersApiAbility, WalletsApiAbility {

  @Test
  void shouldFailWithoutCustomerCredentials() {
    // when
    var response = request("/api/v1/wallets").get();

    // then
    response.then().statusCode(403);
  }

  @Test
  void shouldOpenWallet() {
    // given
    var customer = thereIsCustomer();

    // when
    var response = request("/api/v1/wallets", null, customer).get();

    // then
    response.then().statusCode(200);
  }
}
