package eu.tinylinden.nce.wallets.adapters.rest.v1;

import eu.tinylinden.nce.wallets.core.model.Transaction;
import java.time.Instant;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
class TransactionDto {
  private String id;
  private String ref;
  private String walletId;
  private Instant timestamp;
  private String type;
  private MonetaryAmountDto amount;
  private ExchangeRateDto exchangeRate;

  static TransactionDto fromTransaction(Transaction transaction) {
    return new TransactionDto()
        .setId(transaction.getId().getString())
        .setRef(transaction.getRef().getRaw())
        .setWalletId(transaction.getWallet().getString())
        .setTimestamp(transaction.getTimestamp())
        .setType(transaction.getType().name())
        .setAmount(MonetaryAmountDto.fromMonetaryAmount(transaction.getAmount()))
        .setExchangeRate(ExchangeRateDto.fromExchangeRate(transaction.getExchangeRate()));
  }
}
