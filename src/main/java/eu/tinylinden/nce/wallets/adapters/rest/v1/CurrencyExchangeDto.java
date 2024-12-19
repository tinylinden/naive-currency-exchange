package eu.tinylinden.nce.wallets.adapters.rest.v1;

import eu.tinylinden.nce.wallets.core.model.CurrencyExchange;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
class CurrencyExchangeDto {
  private String operation;
  private MonetaryAmountDto source;
  private MonetaryAmountDto target;
  private ExchangeRateDto rate;

  static CurrencyExchangeDto fromCurrencyExchange(CurrencyExchange exchange) {
    return new CurrencyExchangeDto()
        .setOperation(exchange.getOperation().name())
        .setSource(MonetaryAmountDto.fromMonetaryAmount(exchange.getSource()))
        .setTarget(MonetaryAmountDto.fromMonetaryAmount(exchange.getTarget()))
        .setRate(ExchangeRateDto.fromExchangeRate(exchange.getRate()));
  }
}
