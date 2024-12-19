package eu.tinylinden.nce.wallets.core.model;

import static java.math.BigDecimal.ONE;

import java.math.BigDecimal;
import lombok.Value;

@Value
public class ExchangeRate {
  BigDecimal value;
  String source;

  public static ExchangeRate None = new ExchangeRate(ONE, null);
}
