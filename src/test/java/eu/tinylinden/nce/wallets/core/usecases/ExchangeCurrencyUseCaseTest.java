package eu.tinylinden.nce.wallets.core.usecases;


import eu.tinylinden.nce.TraitsAware;
import eu.tinylinden.nce.commons.model.CurrencyCode;
import eu.tinylinden.nce.customers.core.model.CustomerNoFixtures;
import eu.tinylinden.nce.customers.core.ports.TransactionRepositoryTrait;
import eu.tinylinden.nce.wallets.core.model.CurrencyExchange;
import eu.tinylinden.nce.wallets.core.model.ExchangeRate;
import eu.tinylinden.nce.wallets.core.model.TransactionRef;
import eu.tinylinden.nce.wallets.core.model.WalletFixtures;
import eu.tinylinden.nce.wallets.core.ports.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;

class ExchangeCurrencyUseCaseTest extends TraitsAware
    implements WalletRepositoryTrait, TransactionRepositoryTrait {

  private final WalletRepository wallets = wallets();

  private final TransactionRepository transactions = transactions();

//  private final ExchangeRateFinder exchangeRates =
//      (op, source, target) -> new ExchangeRate(BigDecimal.ONE, "TEST");
//
//  private final ExchangeCurrencyUseCase tested =
//      new ExchangeCurrencyUseCase(wallets, transactions, exchangeRates);

  @BeforeEach
  void setUp() {
    walletExists(WalletFixtures.pln());
    walletExists(WalletFixtures.usd());
  }

  // todo: implement us, please

  private ExchangeCurrencyUseCase.NewCurrencyExchange newExchange(
      CurrencyExchange.Operation operation, CurrencyCode source, CurrencyCode target) {
    return new ExchangeCurrencyUseCase.NewCurrencyExchange()
        .setOwner(CustomerNoFixtures.fixed())
        .setRef(TransactionRef.parse(null))
        .setOperation(operation)
        .setSource(source)
        .setTarget(target)
        .setAmount(BigDecimal.ONE);
  }
}
