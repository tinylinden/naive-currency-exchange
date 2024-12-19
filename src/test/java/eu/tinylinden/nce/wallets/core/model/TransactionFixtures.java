package eu.tinylinden.nce.wallets.core.model;

import static java.math.BigDecimal.ONE;

import eu.tinylinden.nce.FixturesBase;
import eu.tinylinden.nce.commons.model.MonetaryAmount;
import java.time.Instant;

public class TransactionFixtures extends FixturesBase {
  public static Transaction currencyExchange(TransactionRef ref, Wallet wallet) {
    return new Transaction()
        .setId(TransactionId.next())
        .setRef(ref)
        .setWallet(wallet.getId())
        .setTimestamp(Instant.now())
        .setType(Transaction.Type.CURRENCY_EXCHANGE)
        .setAmount(new MonetaryAmount(wallet.getCurrency(), ONE))
        .setExchangeRate(ExchangeRateFixtures.nbpUsd());
  }
}
