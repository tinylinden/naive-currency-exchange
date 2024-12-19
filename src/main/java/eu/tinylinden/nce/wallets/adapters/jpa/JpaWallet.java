package eu.tinylinden.nce.wallets.adapters.jpa;

import eu.tinylinden.nce.commons.model.CurrencyCode;
import eu.tinylinden.nce.commons.model.CustomerNo;
import eu.tinylinden.nce.commons.model.WalletId;
import eu.tinylinden.nce.wallets.core.model.Wallet;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "wallets")
class JpaWallet {

  @Id private Long id;

  private Long owner;
  private String currency;
  private BigDecimal balance;

  Wallet toWallet() {
    return new Wallet()
        .setId(new WalletId(id))
        .setOwner(new CustomerNo(owner))
        .setCurrency(CurrencyCode.valueOf(currency))
        .setBalance(balance);
  }

  static JpaWallet fromWallet(Wallet wallet) {
    return new JpaWallet()
        .setId(wallet.getId().getRaw())
        .setOwner(wallet.getOwner().getRaw())
        .setCurrency(wallet.getCurrency().name())
        .setBalance(wallet.getBalance());
  }
}
