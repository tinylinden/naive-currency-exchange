package eu.tinylinden.nce.wallets.core.usecases;

import eu.tinylinden.nce.TraitsAware;
import eu.tinylinden.nce.commons.exceptions.DomainException;
import eu.tinylinden.nce.commons.model.CurrencyCode;
import eu.tinylinden.nce.customers.core.model.CustomerNoFixtures;
import eu.tinylinden.nce.customers.core.ports.TransactionRepositoryTrait;
import eu.tinylinden.nce.wallets.core.model.*;
import eu.tinylinden.nce.wallets.core.ports.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

import static eu.tinylinden.nce.commons.model.CurrencyCode.PLN;
import static eu.tinylinden.nce.commons.model.CurrencyCode.USD;
import static eu.tinylinden.nce.wallets.core.model.CurrencyExchange.Operation.SELL;
import static java.math.BigDecimal.ONE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class ExchangeCurrencyUseCaseTest extends TraitsAware
    implements WalletRepositoryTrait, TransactionRepositoryTrait {

  private final WalletRepository wallets = wallets();

  private final TransactionRepository transactions = transactions();

  private final ExchangeRate exchangeRate = new ExchangeRate(ONE, "TEST");
  private final ExchangeRateFinder exchangeRates =
      (o, s, t) -> new ExchangeCalculator(exchangeRate, false);

  private final ExchangeCurrencyUseCase tested =
      new ExchangeCurrencyUseCase(wallets, transactions, exchangeRates);

  @Test
  void shouldFailIfTransactionAlreadyExists() {
    // given
    var newExchange = newExchange(SELL, PLN, USD);

    // and
    var plnWallet = walletExists(WalletFixtures.pln());
    transactionExist(TransactionFixtures.currencyExchange(newExchange.getRef(), plnWallet));

    // when
    var actual = catchThrowableOfType(DomainException.class, () -> tested.execute(newExchange));

    // then
    assertThat(actual.getCode()).isEqualTo("DuplicatedTransaction");

    // and
    noWalletsWereSaved();
    noTransactionsWereSaved();
  }

  @Test
  void shouldFailIfWalletForCurrencyDoesNotExist() {
    // given
    var newExchange = newExchange(SELL, PLN, USD);

    // when
    var actual = catchThrowableOfType(DomainException.class, () -> tested.execute(newExchange));

    // then
    assertThat(actual.getCode()).isEqualTo("WalletForCurrencyNotFound");

    // and
    noWalletsWereSaved();
    noTransactionsWereSaved();
  }

  @Test
  void shouldFailIfInsufficientFundsFunds() {
    // given
    var newExchange = newExchange(SELL, PLN, USD);

    // and
    walletExists(WalletFixtures.pln(BigDecimal.valueOf(42.00)));
    walletExists(WalletFixtures.usd());

    // when
    var actual = catchThrowableOfType(DomainException.class, () -> tested.execute(newExchange));

    // then
    assertThat(actual.getCode()).isEqualTo("InsufficientFunds");

    // and
    noWalletsWereSaved();
    noTransactionsWereSaved();
  }

  @Test
  void shouldSucceed() {
    // given
    var newExchange = newExchange(SELL, PLN, USD);

    // and
    var plnWallet = walletExists(WalletFixtures.pln());
    var usdWallet = walletExists(WalletFixtures.usd());

    // when
    var actual = tested.execute(newExchange);

    // then
    CurrencyExchangeAssertions.assertThat(actual)
        .hasOperation(newExchange.getOperation())
        .hasSourceCurrency(PLN)
        .hasSourceValue(BigDecimal.valueOf(100.00))
        .hasTargetCurrency(USD)
        .hasTargetValue(BigDecimal.valueOf(100.00))
        .hasRate(exchangeRate);

    // and - wallet balances were updated
    walletWasSaved(plnWallet);
    walletWasSaved(usdWallet);

    // and - transactions were added
    transactionsWereSaved(2);
  }

  private ExchangeCurrencyUseCase.NewCurrencyExchange newExchange(
      CurrencyExchange.Operation operation, CurrencyCode source, CurrencyCode target) {
    return new ExchangeCurrencyUseCase.NewCurrencyExchange()
        .setOwner(CustomerNoFixtures.fixed())
        .setRef(TransactionRef.parse(null))
        .setOperation(operation)
        .setSource(source)
        .setTarget(target)
        .setAmount(BigDecimal.valueOf(100.00));
  }
}
