package eu.tinylinden.nce.wallets.core.usecases;

import static eu.tinylinden.nce.wallets.core.model.WalletAssertions.assertThat;
import static org.assertj.core.api.Assertions.*;

import eu.tinylinden.nce.TraitsAware;
import eu.tinylinden.nce.commons.exceptions.DomainException;
import eu.tinylinden.nce.commons.model.CurrencyCode;
import eu.tinylinden.nce.commons.model.MonetaryAmount;
import eu.tinylinden.nce.customers.core.model.CustomerNoFixtures;
import eu.tinylinden.nce.customers.core.ports.TransactionRepositoryTrait;
import eu.tinylinden.nce.wallets.core.model.WalletFixtures;
import eu.tinylinden.nce.wallets.core.ports.TransactionRepository;
import eu.tinylinden.nce.wallets.core.ports.WalletRepository;
import eu.tinylinden.nce.wallets.core.ports.WalletRepositoryTrait;
import java.math.BigDecimal;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OpenWalletUseCaseTest extends TraitsAware
    implements WalletRepositoryTrait, TransactionRepositoryTrait {

  private final WalletRepository wallets = wallets();

  private final TransactionRepository transactions = transactions();

  private final OpenWalletUseCase tested = new OpenWalletUseCase(wallets, transactions);

  @Test
  void shouldOpenWallet() {
    // given
    var newWallet = newWallet(BigDecimal.ZERO);

    // when
    var actual = tested.execute(newWallet);

    // then
    assertThat(actual).hasWalletId().matches(newWallet);

    // and
    walletWasSaved(actual);
    noTransactionsWereSaved();
  }

  @Test
  void shouldOpenWalletAndRegisterSeedTransaction() {
    // given
    var newWallet = newWallet(BigDecimal.TEN);

    // when
    var actual = tested.execute(newWallet);

    // then
    assertThat(actual).hasWalletId().matches(newWallet);

    // and
    walletWasSaved(actual);
    transactionsWereSaved(1);
  }

  @Test
  void shouldNotAllowToOpenMoreThanOneWalletForTheSameCurrency() {
    // given
    var newWallet = newWallet(BigDecimal.ZERO);

    // and
    walletExists(WalletFixtures.pln());

    // when
    var actual = catchThrowableOfType(DomainException.class, () -> tested.execute(newWallet));

    // then
    Assertions.assertThat(actual.getCode()).isEqualTo("WalletForCurrencyExists");

    // and
    noWalletsWereSaved();
    noTransactionsWereSaved();
  }

  private OpenWalletUseCase.NewWallet newWallet(BigDecimal amount) {
    return new OpenWalletUseCase.NewWallet()
        .setOwner(CustomerNoFixtures.fixed())
        .setSeed(new MonetaryAmount(CurrencyCode.PLN, amount));
  }
}
