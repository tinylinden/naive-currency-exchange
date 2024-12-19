package eu.tinylinden.nce.wallets.adapters.nbp;

import static eu.tinylinden.nce.commons.model.CurrencyCode.PLN;
import static eu.tinylinden.nce.wallets.core.model.CurrencyExchange.Operation.SELL;

import eu.tinylinden.nce.commons.exceptions.DomainException;
import eu.tinylinden.nce.commons.model.CurrencyCode;
import eu.tinylinden.nce.wallets.core.model.CurrencyExchange;
import eu.tinylinden.nce.wallets.core.model.ExchangeCalculator;
import eu.tinylinden.nce.wallets.core.model.ExchangeRate;
import eu.tinylinden.nce.wallets.core.ports.ExchangeRateFinder;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Locale;
import java.util.function.Supplier;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class NbpExchangeRateFinder implements ExchangeRateFinder {

  private final NbpExchangeRateClient nbp;

  @Override
  public ExchangeCalculator find(
      CurrencyExchange.Operation operation, CurrencyCode source, CurrencyCode target) {
    var rate = find(currency(source, target));
    if (operation == SELL) {
      return calculator(rate.getNo(), rate::getBid, source == PLN);
    }
    return calculator(rate.getNo(), rate::getAsk, target == PLN);
  }

  private ExchangeCalculator calculator(String source, Supplier<BigDecimal> value, Boolean invert) {
    return new ExchangeCalculator(new ExchangeRate(value.get(), source), invert);
  }

  private CurrencyCode currency(CurrencyCode source, CurrencyCode target) {
    return Stream.of(source, target)
        .filter(it -> it != PLN)
        .findFirst()
        .orElseThrow(
            () ->
                new DomainException(
                    "UnsupportedCurrencyPair", Collections.emptyMap(), Locale.getDefault()));
  }

  private NbpExchangeRateDto find(CurrencyCode currency) {
    try {
      log.trace("Fetching exchange rate for {}", currency);
      return nbp.getRates(currency.name()).getRates().getFirst();
    } catch (Exception ex) {
      log.error("Fetching exchange rate failed", ex);
      throw new DomainException(
          "ExchangeRateUnavailable", Collections.emptyMap(), Locale.getDefault());
    }
  }
}
