package eu.tinylinden.nce.wallets.core.model;

import eu.tinylinden.nce.commons.model.MonetaryAmount;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CurrencyExchange {
  private Operation operation;
  private MonetaryAmount source;
  private MonetaryAmount target;
  private ExchangeRate rate;

  public enum Operation {
    SELL,
    BUY
  }
}
