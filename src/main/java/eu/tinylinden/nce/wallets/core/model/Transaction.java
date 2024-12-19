package eu.tinylinden.nce.wallets.core.model;

import eu.tinylinden.nce.commons.model.MonetaryAmount;
import eu.tinylinden.nce.commons.model.WalletId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;

@Data
@Accessors(chain = true)
public class Transaction {
  TransactionId id;
  TransactionRef ref;
  WalletId wallet;
  Instant timestamp;
  Type type;
  MonetaryAmount amount;
  ExchangeRate exchangeRate;

  public enum Type {
    TOP_UP,
    CURRENCY_EXCHANGE,
  }
}
