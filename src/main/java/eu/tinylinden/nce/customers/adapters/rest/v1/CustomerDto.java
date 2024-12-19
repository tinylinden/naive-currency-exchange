package eu.tinylinden.nce.customers.adapters.rest.v1;

import eu.tinylinden.nce.customers.core.model.Customer;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
class CustomerDto {
  private String customerNo;
  private String firstName;
  private String lastName;

  static CustomerDto fromCustomer(Customer customer) {
    return new CustomerDto()
        .setCustomerNo(customer.getCustomerNo().getString())
        .setFirstName(customer.getFirstName())
        .setLastName(customer.getLastName());
  }
}
