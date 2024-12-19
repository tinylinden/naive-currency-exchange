package eu.tinylinden.nce.wallets.core.ports;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import eu.tinylinden.nce.Trait;
import eu.tinylinden.nce.wallets.core.model.Wallet;
import java.util.Optional;

public interface WalletRepositoryTrait extends Trait {
  default WalletRepository wallets() {
    var mock = dependency(WalletRepository.class, () -> mock(WalletRepository.class));

    // happy-path - every save succeeds
    when(mock.save(any())).thenAnswer(it -> it.getArguments()[0]);

    return mock;
  }

  default Wallet walletExists(Wallet wallet) {
    var mock = wallets();

    var byId = new WalletFinder.Specification.ByOwnerAndId(wallet.getOwner(), wallet.getId());
    var byCurrency =
        new WalletFinder.Specification.ByOwnerAndCurrency(wallet.getOwner(), wallet.getCurrency());

    when(mock.find(byId)).thenReturn(Optional.of(wallet));
    when(mock.find(byCurrency)).thenReturn(Optional.of(wallet));

    return wallet;
  }

  default void walletWasSaved(Wallet wallet) {
    verify(wallets()).save(wallet);
  }

  default void noWalletsWereSaved() {
    verify(wallets(), times(0)).save(any());
  }
}
