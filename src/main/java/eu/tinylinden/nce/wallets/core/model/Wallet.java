package eu.tinylinden.nce.wallets.core.model;

import eu.tinylinden.nce.commons.model.CurrencyCode;
import eu.tinylinden.nce.commons.model.CustomerNo;
import eu.tinylinden.nce.commons.model.WalletId;
import java.math.BigDecimal;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Wallet {
  private WalletId id;
  private CustomerNo owner;
  private CurrencyCode currency;
  private BigDecimal balance;

  public Wallet incBalance(BigDecimal delta) {
    return setBalance(balance.add(delta));
  }

  public Wallet decBalance(BigDecimal delta) {
    return setBalance(balance.subtract(delta));
  }
}
