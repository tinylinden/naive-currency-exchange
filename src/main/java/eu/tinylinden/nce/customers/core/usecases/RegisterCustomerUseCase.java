package eu.tinylinden.nce.customers.core.usecases;

import eu.tinylinden.nce.commons.model.CustomerNo;
import eu.tinylinden.nce.commons.usecases.UseCase;
import eu.tinylinden.nce.customers.core.model.Customer;
import eu.tinylinden.nce.customers.core.ports.CustomerRepository;
import eu.tinylinden.nce.customers.core.ports.PassEncoder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RegisterCustomerUseCase
    implements UseCase<RegisterCustomerUseCase.NewCustomer, Customer> {

  private final PassEncoder passEncoder;
  private final CustomerRepository customers;

  @Override
  public Customer execute(final NewCustomer input) {
    var customer = input.customer(passEncoder);
    log.debug("Customer {} registered", customer.getCustomerNo().getString());
    return customers.save(customer);
  }

  @Data
  @Accessors(chain = true)
  public static class NewCustomer {
    private String firstName;
    private String lastName;
    private String password;

    private Customer customer(PassEncoder passEncoder) {
      return new Customer()
          .setCustomerNo(CustomerNo.next())
          .setFirstName(getFirstName())
          .setLastName(getLastName())
          .setPassword(passEncoder.apply(getPassword()));
    }
  }
}
