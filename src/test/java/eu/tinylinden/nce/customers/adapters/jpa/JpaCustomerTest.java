package eu.tinylinden.nce.customers.adapters.jpa;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import eu.tinylinden.nce.customers.core.model.CustomerFixtures;
import org.junit.jupiter.api.Test;

class JpaCustomerTest {

  @Test
  void shouldMapCustomerToJpaAndBackAgain() {
    // given
    var original = CustomerFixtures.random();

    // when
    var actual = JpaCustomer.fromCustomer(original).toCustomer();

    // then
    assertThat(actual).isEqualTo(original);
  }
}
