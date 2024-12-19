package eu.tinylinden.nce.customers.core.model;

import eu.tinylinden.nce.FixturesBase;
import eu.tinylinden.nce.commons.model.CustomerNo;

public class CustomerFixtures extends FixturesBase {
  public static Customer random() {
    return new Customer()
        .setCustomerNo(CustomerNo.next())
        .setFirstName(faker.name().firstName())
        .setLastName(faker.name().lastName())
        .setPassword("secret");
  }
}
