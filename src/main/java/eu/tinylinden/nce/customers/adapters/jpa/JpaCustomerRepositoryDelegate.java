package eu.tinylinden.nce.customers.adapters.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCustomerRepositoryDelegate extends JpaRepository<JpaCustomer, Long> {}
