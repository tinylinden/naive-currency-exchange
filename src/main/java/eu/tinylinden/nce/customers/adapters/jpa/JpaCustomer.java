package eu.tinylinden.nce.customers.adapters.jpa;

import eu.tinylinden.nce.commons.model.CustomerNo;
import eu.tinylinden.nce.customers.core.model.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "customers")
class JpaCustomer {

  @Id private Long id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  private String password;

  Customer toCustomer() {
    return new Customer()
        .setCustomerNo(new CustomerNo(id))
        .setFirstName(firstName)
        .setLastName(lastName)
        .setPassword(password);
  }

  static JpaCustomer fromCustomer(Customer customer) {
    return new JpaCustomer()
        .setId(customer.getCustomerNo().getRaw())
        .setFirstName(customer.getFirstName())
        .setLastName(customer.getLastName())
        .setPassword(customer.getPassword());
  }
}
