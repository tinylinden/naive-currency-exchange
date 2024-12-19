package eu.tinylinden.nce.wallets.core.model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.function.Function;

import lombok.Value;

@Value
public class ExchangeCalculator implements Function<BigDecimal, BigDecimal> {
  ExchangeRate rate;
  Boolean invert;

  @Override
  public BigDecimal apply(BigDecimal amount) {
    if (invert) {
      return BigDecimal.ONE
          .divide(rate.getValue(), new MathContext(4))
          .multiply(amount)
          .setScale(2, RoundingMode.HALF_UP);
    }
    return rate.getValue().multiply(amount).setScale(2, RoundingMode.HALF_UP);
  }
}
