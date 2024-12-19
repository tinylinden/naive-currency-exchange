package eu.tinylinden.nce.customers.core.model;

import eu.tinylinden.nce.customers.core.ports.PassEncoder;
import eu.tinylinden.nce.customers.core.usecases.RegisterCustomerUseCase;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class CustomerAssertions extends AbstractAssert<CustomerAssertions, Customer> {
  private CustomerAssertions(Customer actual) {
    super(actual, CustomerAssertions.class);
  }

  public static CustomerAssertions assertThat(Customer actual) {
    return new CustomerAssertions(actual);
  }

  public CustomerAssertions hasCustomerNo() {
    Assertions.assertThat(actual.getCustomerNo()).isNotNull();
    return this;
  }

  public CustomerAssertions matches(
      RegisterCustomerUseCase.NewCustomer given, PassEncoder passEncoder) {
    Assertions.assertThat(actual.getFirstName()).isEqualTo(given.getFirstName());
    Assertions.assertThat(actual.getLastName()).isEqualTo(given.getLastName());
    Assertions.assertThat(actual.getPassword()).isEqualTo(passEncoder.apply(given.getPassword()));
    return this;
  }
}
