package eu.tinylinden.nce.wallets.adapters.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

interface JpaWalletRepositoryDelegate extends JpaRepository<JpaWallet, Long> {
  List<JpaWallet> findByOwner(Long owner);

  Optional<JpaWallet> findByOwnerAndCurrency(Long owner, String currency);

  Optional<JpaWallet> findByOwnerAndId(Long owner, Long id);
}
