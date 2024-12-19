package eu.tinylinden.nce.wallets.core.ports;

import eu.tinylinden.nce.commons.model.CurrencyCode;
import eu.tinylinden.nce.wallets.core.model.CurrencyExchange;
import eu.tinylinden.nce.wallets.core.model.ExchangeRate;

@FunctionalInterface
public interface ExchangeRateFinder {
  ExchangeRate find(CurrencyExchange.Operation operation, CurrencyCode source, CurrencyCode target);
}
