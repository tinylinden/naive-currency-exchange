package eu.tinylinden.nce.wallets.core.ports;

import eu.tinylinden.nce.wallets.core.model.Wallet;

public interface WalletRepository extends WalletFinder {
  Wallet save(Wallet wallet);
}
