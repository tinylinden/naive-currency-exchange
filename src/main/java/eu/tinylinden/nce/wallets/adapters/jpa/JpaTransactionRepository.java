package eu.tinylinden.nce.wallets.adapters.jpa;

import eu.tinylinden.nce.commons.model.WalletId;
import eu.tinylinden.nce.wallets.core.model.Transaction;
import eu.tinylinden.nce.wallets.core.ports.TransactionRepository;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaTransactionRepository implements TransactionRepository {

  private final JpaTransactionRepositoryDelegate delegate;

  @Override
  public List<Transaction> list(WalletId wallet) {
    return delegate.findByWallet(wallet.getRaw()).stream()
        .map(JpaTransaction::toTransaction)
        .toList();
  }

  @Override
  public List<Transaction> list(Specification specification) {
    final List<JpaTransaction> found =
        switch (specification) {
          case Specification.ByRef it -> delegate.findByRef(it.getRef().getRaw());

          default -> Collections.emptyList();
        };
    return found.stream().map(JpaTransaction::toTransaction).toList();
  }

  @Override
  public Transaction save(Transaction transaction) {
    delegate.save(JpaTransaction.fromTransaction(transaction));
    return transaction;
  }
}
