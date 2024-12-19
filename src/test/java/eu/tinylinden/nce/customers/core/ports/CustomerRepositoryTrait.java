package eu.tinylinden.nce.customers.core.ports;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import eu.tinylinden.nce.Trait;
import eu.tinylinden.nce.customers.core.model.Customer;

public interface CustomerRepositoryTrait extends Trait {
  default CustomerRepository customers() {
    var mock = dependency(CustomerRepository.class, () -> mock(CustomerRepository.class));

    // happy-path - every save succeeds
    when(mock.save(any())).thenAnswer(it -> it.getArguments()[0]);

    return mock;
  }

  default void customerWasSaved(Customer customer) {
    verify(customers()).save(customer);
  }
}
