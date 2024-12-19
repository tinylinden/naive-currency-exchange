package eu.tinylinden.nce.customers.core.model;

import eu.tinylinden.nce.commons.model.CustomerNo;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Customer {
  private CustomerNo customerNo;
  private String firstName;
  private String lastName;
  private String password;
}
