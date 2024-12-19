package eu.tinylinden.nce.wallets.adapters.nbp;

import static eu.tinylinden.nce.commons.model.CurrencyCode.PLN;
import static eu.tinylinden.nce.wallets.core.model.CurrencyExchange.Operation.BUY;
import static eu.tinylinden.nce.wallets.core.model.CurrencyExchange.Operation.SELL;

import eu.tinylinden.nce.commons.exceptions.DomainException;
import eu.tinylinden.nce.commons.model.CurrencyCode;
import eu.tinylinden.nce.wallets.core.model.CurrencyExchange;
import eu.tinylinden.nce.wallets.core.model.ExchangeRate;
import eu.tinylinden.nce.wallets.core.ports.ExchangeRateFinder;
import java.util.Collections;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class NbpExchangeRateFinder implements ExchangeRateFinder {

  private final NbpExchangeRateClient nbp;

  // fixme: refactor me, please (i was really tired implementing this ugly if-leton)
  // todo: implement tests
  @Override
  public ExchangeRate find(
      CurrencyExchange.Operation operation, CurrencyCode source, CurrencyCode target) {
    if (operation == BUY && target == PLN) {
      return buyLocal(source);
    }

    if (operation == BUY) {
      return buyForeign(target);
    }

    if (operation == SELL && source == PLN) {
      return sellLocal(target);
    }

    if (operation == SELL) {
      return sellForeign(source);
    }

    throw new DomainException(
        "UnsupportedCurrencyPair", Collections.emptyMap(), Locale.getDefault());
  }

  private ExchangeRate buyLocal(CurrencyCode currency) {
    var rate = find(currency);
    return new ExchangeRate(rate.getBid(), rate.getNo());
  }

  private ExchangeRate buyForeign(CurrencyCode currency) {
    var rate = find(currency);
    return new ExchangeRate(rate.getBid(), rate.getNo());
  }

  private ExchangeRate sellLocal(CurrencyCode currency) {
    var rate = find(currency);
    return new ExchangeRate(rate.getAsk(), rate.getNo());
  }

  private ExchangeRate sellForeign(CurrencyCode currency) {
    var rate = find(currency);
    return new ExchangeRate(rate.getAsk(), rate.getNo());
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
