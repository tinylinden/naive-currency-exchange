package eu.tinylinden.nce.wallets.adapters.rest.v1;

import eu.tinylinden.nce.wallets.core.model.ExchangeRate;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
class ExchangeRateDto {
  private String source;
  private BigDecimal value;

  static ExchangeRateDto fromExchangeRate(ExchangeRate exchangeRate) {
    return new ExchangeRateDto()
        .setSource(exchangeRate.getSource())
        .setValue(exchangeRate.getValue());
  }
}
