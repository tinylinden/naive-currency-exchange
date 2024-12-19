package eu.tinylinden.nce.commons.model;

import java.math.BigDecimal;
import lombok.Value;

@Value
public class MonetaryAmount {
  CurrencyCode currency;
  BigDecimal value;

  public MonetaryAmount negate() {
    return new MonetaryAmount(currency, value.negate());
  }
}
