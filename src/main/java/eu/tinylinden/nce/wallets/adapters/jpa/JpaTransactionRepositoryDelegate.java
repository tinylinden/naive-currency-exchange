package eu.tinylinden.nce.wallets.adapters.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface JpaTransactionRepositoryDelegate extends JpaRepository<JpaTransaction, Long> {
  List<JpaTransaction> findByWallet(Long wallet);

  List<JpaTransaction> findByRef(String ref);
}
