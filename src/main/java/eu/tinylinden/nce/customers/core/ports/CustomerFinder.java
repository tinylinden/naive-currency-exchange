package eu.tinylinden.nce.customers.core.ports;

import eu.tinylinden.nce.commons.model.CustomerNo;
import eu.tinylinden.nce.customers.core.model.Customer;

import java.util.Optional;

@FunctionalInterface
public interface CustomerFinder {
  Optional<Customer> find(CustomerNo customerNo);
}
