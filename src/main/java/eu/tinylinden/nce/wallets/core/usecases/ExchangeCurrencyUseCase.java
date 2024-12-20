package eu.tinylinden.nce.wallets.core.usecases;

import eu.tinylinden.nce.commons.exceptions.DomainException;
import eu.tinylinden.nce.commons.model.CurrencyCode;
import eu.tinylinden.nce.commons.model.CustomerNo;
import eu.tinylinden.nce.commons.model.MonetaryAmount;
import eu.tinylinden.nce.commons.usecases.UseCase;
import eu.tinylinden.nce.wallets.core.model.*;
import eu.tinylinden.nce.wallets.core.ports.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Locale;
import java.util.Map;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExchangeCurrencyUseCase
    implements UseCase<ExchangeCurrencyUseCase.NewCurrencyExchange, CurrencyExchange> {

  private final WalletRepository wallets;
  private final TransactionRepository transactions;
  private final ExchangeRateFinder exchangeRates;

  @Transactional
  @Override
  public CurrencyExchange execute(final NewCurrencyExchange input) {
    checkIfTransactionsExist(input);

    var sourceWallet = findWallet(input.owner, input.getSource());
    var targetWallet = findWallet(input.owner, input.getTarget());
    var calculator = exchangeCalculator(input);

    var now = Instant.now();
    var debitTransaction = debitTransaction(sourceWallet, calculator, input, now);
    var creditTransaction = creditTransaction(targetWallet, calculator, input, now);

    return new CurrencyExchange()
        .setOperation(input.getOperation())
        .setSource(debitTransaction.getAmount().negate())
        .setTarget(creditTransaction.getAmount())
        .setRate(calculator.getRate());
  }

  private void checkIfTransactionsExist(final NewCurrencyExchange input) {
    var existing = transactions.list(new TransactionFinder.Specification.ByRef(input.getRef()));
    if (!existing.isEmpty()) {
      throw new DomainException(
          "DuplicatedTransaction",
          Map.of("reference", input.getRef().getRaw()),
          Locale.getDefault());
    }
  }

  private Wallet findWallet(final CustomerNo owner, final CurrencyCode currency) {
    log.trace("Finding {} wallet for {}", currency, owner.getString());
    return wallets
        .find(new WalletFinder.Specification.ByOwnerAndCurrency(owner, currency))
        .orElseThrow(
            () ->
                new DomainException(
                    "WalletForCurrencyNotFound",
                    Map.of("currency", currency),
                    Locale.getDefault()));
  }

  private ExchangeCalculator exchangeCalculator(final NewCurrencyExchange input) {
    log.trace(
        "Finding exchange calculator for {} {}/{}",
        input.getOperation(),
        input.getSource(),
        input.getTarget());
    return exchangeRates.find(input.getOperation(), input.getSource(), input.getTarget());
  }

  private Transaction debitTransaction(
      final Wallet wallet,
      final ExchangeCalculator calculator,
      final NewCurrencyExchange input,
      final Instant now) {
    BigDecimal effectiveAmount =
        switch (input.getOperation()) {
          case BUY -> calculator.apply(input.getAmount());
          case SELL -> input.getAmount();
        };

    if (wallet.getBalance().subtract(effectiveAmount).signum() == -1) {
      throw new DomainException(
          "InsufficientFunds", Map.of("currency", wallet.getCurrency()), Locale.getDefault());
    }

    log.trace("Debiting {}", wallet.getId().getString());
    wallets.save(wallet.decBalance(effectiveAmount));
    return transactions.save(
        transaction(input.getRef(), wallet, now, effectiveAmount.negate(), calculator.getRate()));
  }

  private Transaction creditTransaction(
      final Wallet wallet,
      final ExchangeCalculator calculator,
      final NewCurrencyExchange input,
      final Instant now) {
    BigDecimal effectiveAmount =
        switch (input.getOperation()) {
          case BUY -> input.getAmount();
          case SELL -> calculator.apply(input.getAmount());
        };

    log.trace("Crediting {}", wallet.getId().getString());
    wallets.save(wallet.incBalance(effectiveAmount));
    return transactions.save(
        transaction(input.getRef(), wallet, now, effectiveAmount, calculator.getRate()));
  }

  private Transaction transaction(
      final TransactionRef ref,
      final Wallet wallet,
      final Instant now,
      final BigDecimal amount,
      final ExchangeRate rate) {
    return new Transaction()
        .setId(TransactionId.next())
        .setRef(ref)
        .setWallet(wallet.getId())
        .setTimestamp(now)
        .setType(Transaction.Type.CURRENCY_EXCHANGE)
        .setAmount(new MonetaryAmount(wallet.getCurrency(), amount))
        .setExchangeRate(rate);
  }

  @Data
  @Accessors(chain = true)
  public static class NewCurrencyExchange {
    private CustomerNo owner;
    private TransactionRef ref;
    private CurrencyExchange.Operation operation;
    private CurrencyCode source;
    private CurrencyCode target;
    private BigDecimal amount;
  }
}
