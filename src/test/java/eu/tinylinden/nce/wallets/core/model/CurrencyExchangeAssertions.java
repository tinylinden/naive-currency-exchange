package eu.tinylinden.nce.wallets.core.model;

import eu.tinylinden.nce.commons.model.CurrencyCode;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import java.math.BigDecimal;

public class CurrencyExchangeAssertions
    extends AbstractAssert<CurrencyExchangeAssertions, CurrencyExchange> {
  private CurrencyExchangeAssertions(CurrencyExchange actual) {
    super(actual, CurrencyExchangeAssertions.class);
  }

  public static CurrencyExchangeAssertions assertThat(CurrencyExchange actual) {
    return new CurrencyExchangeAssertions(actual);
  }

  public CurrencyExchangeAssertions hasOperation(CurrencyExchange.Operation expected) {
    Assertions.assertThat(actual.getOperation()).isEqualTo(expected);
    return this;
  }

  public CurrencyExchangeAssertions hasSourceCurrency(CurrencyCode expected) {
    Assertions.assertThat(actual.getSource().getCurrency()).isEqualTo(expected);
    return this;
  }

  public CurrencyExchangeAssertions hasSourceValue(BigDecimal expected) {
    Assertions.assertThat(actual.getSource().getValue())
        .usingComparator(BigDecimal::compareTo)
        .isEqualTo(expected);
    return this;
  }

  public CurrencyExchangeAssertions hasTargetCurrency(CurrencyCode expected) {
    Assertions.assertThat(actual.getTarget().getCurrency()).isEqualTo(expected);
    return this;
  }

  public CurrencyExchangeAssertions hasTargetValue(BigDecimal expected) {
    Assertions.assertThat(actual.getTarget().getValue())
        .usingComparator(BigDecimal::compareTo)
        .isEqualTo(expected);
    return this;
  }

  public CurrencyExchangeAssertions hasRate(ExchangeRate expected) {
    Assertions.assertThat(actual.getRate()).isEqualTo(expected);
    return this;
  }
}
