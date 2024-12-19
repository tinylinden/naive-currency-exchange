package eu.tinylinden.nce.customers.adapters.jpa;

import eu.tinylinden.nce.commons.model.CustomerNo;
import eu.tinylinden.nce.customers.core.model.Customer;
import eu.tinylinden.nce.customers.core.ports.CustomerRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class JpaCustomerRepository implements CustomerRepository {

  private final JpaCustomerRepositoryDelegate delegate;

  @Override
  public Optional<Customer> find(final CustomerNo customerNo) {
    return delegate.findById(customerNo.getRaw()).map(JpaCustomer::toCustomer);
  }

  @Override
  public Customer save(final Customer customer) {
    delegate.save(JpaCustomer.fromCustomer(customer));
    return customer;
  }
}
