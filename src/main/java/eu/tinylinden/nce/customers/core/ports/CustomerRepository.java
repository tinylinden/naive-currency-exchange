package eu.tinylinden.nce.customers.core.ports;

import eu.tinylinden.nce.customers.core.model.Customer;

public interface CustomerRepository extends CustomerFinder {
  Customer save(Customer customer);
}
