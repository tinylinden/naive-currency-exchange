package eu.tinylinden.nce.customers.core.usecases;

import static eu.tinylinden.nce.FixturesBase.faker;

import eu.tinylinden.nce.TraitsAware;
import eu.tinylinden.nce.customers.core.model.CustomerAssertions;
import eu.tinylinden.nce.customers.core.ports.CustomerRepository;
import eu.tinylinden.nce.customers.core.ports.CustomerRepositoryTrait;
import eu.tinylinden.nce.customers.core.ports.PassEncoder;
import org.junit.jupiter.api.Test;

class RegisterCustomerUseCaseTest extends TraitsAware implements CustomerRepositoryTrait {

  private final CustomerRepository customers = customers();

  private final PassEncoder passEncoder = it -> "ENC:" + it;

  private final RegisterCustomerUseCase tested =
      new RegisterCustomerUseCase(passEncoder, customers);

  @Test
  void shouldRegisterCustomer() {
    // given
    var given = newCustomer();

    // when
    var actual = tested.execute(given);

    // then
    CustomerAssertions.assertThat(actual).hasCustomerNo().matches(given, passEncoder);

    // and
    customerWasSaved(actual);
  }

  private RegisterCustomerUseCase.NewCustomer newCustomer() {
    return new RegisterCustomerUseCase.NewCustomer()
        .setFirstName(faker.name().firstName())
        .setLastName(faker.name().lastName())
        .setPassword(faker.internet().password());
  }
}
