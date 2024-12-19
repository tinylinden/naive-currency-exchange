package eu.tinylinden.nce.wallets.adapters.rest.v1;

import eu.tinylinden.nce.wallets.core.model.Wallet;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
class WalletDto {
  private String walletId;
  private String currency;
  private BigDecimal balance;

  static WalletDto fromWallet(Wallet wallet) {
    return new WalletDto()
        .setWalletId(wallet.getId().getString())
        .setCurrency(wallet.getCurrency().name())
        .setBalance(wallet.getBalance());
  }
}
