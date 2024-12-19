package eu.tinylinden.nce.wallets.core.model;

import eu.tinylinden.nce.FixturesBase;

import java.math.BigDecimal;

public class ExchangeRateFixtures extends FixturesBase {
  public static ExchangeRate nbpUsd() {
    return new ExchangeRate(BigDecimal.valueOf(4.104), "246/C/NBP/2024");
  }
}
