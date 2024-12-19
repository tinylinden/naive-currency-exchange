package eu.tinylinden.nce.customers.adapters.rest.v1;

import eu.tinylinden.nce.RestAssuredAbility;

import static eu.tinylinden.nce.FakerHolder.faker;
import static eu.tinylinden.nce.ObjectMapperHolder.jsonString;

public interface CustomersApiAbility extends RestAssuredAbility {
  default String registerCustomerRequest() {
    var request =
        new RegisterCustomerEndpoint.Request()
            .setFirstName(faker.name().firstName())
            .setLastName(faker.name().lastName())
            .setPassword(DEFAULT_CUSTOMER_PASSWORD);

    return jsonString(request);
  }

  default String thereIsCustomer() {
    var customer = request("/api/v1/customers", registerCustomerRequest(), null).post();
    return extractString(customer, "customerNo");
  }
}
