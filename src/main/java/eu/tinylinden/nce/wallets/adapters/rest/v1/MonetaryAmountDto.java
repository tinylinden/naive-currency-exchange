package eu.tinylinden.nce.wallets.adapters.rest.v1;

import eu.tinylinden.nce.commons.model.CurrencyCode;
import eu.tinylinden.nce.commons.model.MonetaryAmount;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
class MonetaryAmountDto {
  private String currency;
  private BigDecimal value;

  MonetaryAmount toMonetaryAmount() {
    return new MonetaryAmount(CurrencyCode.valueOf(currency), value);
  }

  static MonetaryAmountDto fromMonetaryAmount(MonetaryAmount amount) {
    return new MonetaryAmountDto()
        .setCurrency(amount.getCurrency().name())
        .setValue(amount.getValue());
  }
}
