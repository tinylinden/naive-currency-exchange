package eu.tinylinden.nce.wallets.adapters.jpa;

import eu.tinylinden.nce.commons.model.CustomerNo;
import eu.tinylinden.nce.wallets.core.model.Wallet;
import eu.tinylinden.nce.wallets.core.ports.WalletRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class JpaWalletRepository implements WalletRepository {

  private final JpaWalletRepositoryDelegate delegate;

  @Override
  public List<Wallet> list(final CustomerNo owner) {
    return delegate.findByOwner(owner.getRaw()).stream().map(JpaWallet::toWallet).toList();
  }

  @Override
  public Optional<Wallet> find(Specification specification) {
    final Optional<JpaWallet> found =
        switch (specification) {
          case Specification.ByOwnerAndCurrency it ->
              delegate.findByOwnerAndCurrency(it.getOwner().getRaw(), it.getCurrency().name());

          case Specification.ByOwnerAndId it ->
              delegate.findByOwnerAndId(it.getOwner().getRaw(), it.getId().getRaw());

          default -> Optional.empty();
        };
    return found.map(JpaWallet::toWallet);
  }

  @Override
  public Wallet save(final Wallet wallet) {
    delegate.save(JpaWallet.fromWallet(wallet));
    return wallet;
  }
}
