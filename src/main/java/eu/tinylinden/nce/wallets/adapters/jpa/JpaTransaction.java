package eu.tinylinden.nce.wallets.adapters.jpa;

import eu.tinylinden.nce.commons.model.CurrencyCode;
import eu.tinylinden.nce.commons.model.MonetaryAmount;
import eu.tinylinden.nce.commons.model.WalletId;
import eu.tinylinden.nce.wallets.core.model.ExchangeRate;
import eu.tinylinden.nce.wallets.core.model.Transaction;
import eu.tinylinden.nce.wallets.core.model.TransactionId;
import eu.tinylinden.nce.wallets.core.model.TransactionRef;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "transactions")
class JpaTransaction {

  @Id private Long id;

  private String ref;
  private Long wallet;
  private Instant timestamp;
  private String type;
  private String currency;
  private BigDecimal amount;

  @Column(name = "exchange_rate")
  private BigDecimal exchangeRate;

  @Column(name = "exchange_rate_source")
  private String exchangeRateSource;

  Transaction toTransaction() {
    return new Transaction()
        .setId(new TransactionId(id))
        .setRef(new TransactionRef(ref))
        .setWallet(new WalletId(wallet))
        .setTimestamp(timestamp)
        .setType(Transaction.Type.valueOf(type))
        .setAmount(new MonetaryAmount(CurrencyCode.valueOf(currency), amount))
        .setExchangeRate(new ExchangeRate(exchangeRate, exchangeRateSource));
  }

  static JpaTransaction fromTransaction(Transaction transaction) {
    return new JpaTransaction()
        .setId(transaction.getId().getRaw())
        .setRef(transaction.getRef().getRaw())
        .setWallet(transaction.getWallet().getRaw())
        .setTimestamp(transaction.getTimestamp())
        .setType(transaction.getType().name())
        .setAmount(transaction.getAmount().getValue())
        .setCurrency(transaction.getAmount().getCurrency().name())
        .setExchangeRate(transaction.getExchangeRate().getValue())
        .setExchangeRateSource(transaction.getExchangeRate().getSource());
  }
}
