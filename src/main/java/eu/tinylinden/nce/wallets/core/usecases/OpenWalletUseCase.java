package eu.tinylinden.nce.wallets.core.usecases;

import eu.tinylinden.nce.commons.exceptions.DomainException;
import eu.tinylinden.nce.commons.model.CurrencyCode;
import eu.tinylinden.nce.commons.model.CustomerNo;
import eu.tinylinden.nce.commons.model.MonetaryAmount;
import eu.tinylinden.nce.commons.model.WalletId;
import eu.tinylinden.nce.commons.usecases.UseCase;
import eu.tinylinden.nce.wallets.core.model.*;
import eu.tinylinden.nce.wallets.core.ports.TransactionRepository;
import eu.tinylinden.nce.wallets.core.ports.WalletFinder;
import eu.tinylinden.nce.wallets.core.ports.WalletRepository;
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
public class OpenWalletUseCase implements UseCase<OpenWalletUseCase.NewWallet, Wallet> {

  private final WalletRepository wallets;
  private final TransactionRepository transactions;

  @Transactional
  @Override
  public Wallet execute(final NewWallet input) {
    checkIfWalletExists(input);
    var wallet = openWallet(input);
    registerSeedTransaction(wallet, input.seed);
    log.debug("Wallet {} opened for {}", wallet.getId().getString(), wallet.getOwner().getString());
    return wallet;
  }

  private void checkIfWalletExists(final NewWallet input) {
    wallets
        .find(input.walletSpecification())
        .ifPresent(
            it -> {
              throw new DomainException(
                  "WalletForCurrencyExists",
                  Map.of("currency", it.getCurrency()),
                  Locale.getDefault());
            });
  }

  private Wallet openWallet(final NewWallet input) {
    var wallet =
        new Wallet()
            .setId(WalletId.next())
            .setOwner(input.owner)
            .setCurrency(input.seed.getCurrency())
            .setBalance(input.seed.getValue());
    return wallets.save(wallet);
  }

  private void registerSeedTransaction(final Wallet wallet, final MonetaryAmount balance) {
    if (wallet.getBalance().signum() == 0) {
      return;
    }

    var transaction =
        new Transaction()
            .setId(TransactionId.next())
            .setRef(TransactionRef.parse(null))
            .setWallet(wallet.getId())
            .setTimestamp(Instant.now())
            .setType(Transaction.Type.TOP_UP)
            .setAmount(balance)
            .setExchangeRate(ExchangeRate.None);
    transactions.save(transaction);
  }

  @Data
  @Accessors(chain = true)
  public static class NewWallet {
    private CustomerNo owner;
    private MonetaryAmount seed;

    WalletFinder.Specification walletSpecification() {
      return new WalletFinder.Specification.ByOwnerAndCurrency(owner, seed.getCurrency());
    }
  }
}
