package eu.tinylinden.nce.wallets.core.model;

import eu.tinylinden.nce.FixturesBase;
import eu.tinylinden.nce.commons.model.CurrencyCode;
import eu.tinylinden.nce.commons.model.WalletId;
import eu.tinylinden.nce.customers.core.model.CustomerNoFixtures;

import java.math.BigDecimal;

public class WalletFixtures extends FixturesBase {
  public static Wallet pln() {
    return pln(BigDecimal.valueOf(100));
  }

  public static Wallet pln(BigDecimal balance) {
    return new Wallet()
        .setId(WalletId.next())
        .setOwner(CustomerNoFixtures.fixed())
        .setCurrency(CurrencyCode.PLN)
        .setBalance(balance);
  }

  public static Wallet usd() {
    return usd(BigDecimal.valueOf(100));
  }

  public static Wallet usd(BigDecimal balance) {
    return new Wallet()
        .setId(WalletId.next())
        .setOwner(CustomerNoFixtures.fixed())
        .setCurrency(CurrencyCode.USD)
        .setBalance(balance);
  }
}
