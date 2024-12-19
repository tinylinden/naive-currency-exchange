package eu.tinylinden.nce.customers.core.ports;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import eu.tinylinden.nce.Trait;
import eu.tinylinden.nce.wallets.core.model.Transaction;
import eu.tinylinden.nce.wallets.core.ports.TransactionFinder;
import eu.tinylinden.nce.wallets.core.ports.TransactionRepository;

import java.util.List;

public interface TransactionRepositoryTrait extends Trait {
  default TransactionRepository transactions() {
    var mock = dependency(TransactionRepository.class, () -> mock(TransactionRepository.class));

    // happy path - every save succeeds
    when(mock.save(any())).thenAnswer(it -> it.getArguments()[0]);

    return mock;
  }

  default Transaction transactionExist(Transaction transaction) {
    var mock = transactions();

    var byRef = new TransactionFinder.Specification.ByRef(transaction.getRef());

    when(mock.list(byRef)).thenReturn(List.of(transaction));

    return transaction;
  }

  default void transactionsWereSaved(Integer count) {
    verify(transactions(), times(count)).save(any());
  }

  default void noTransactionsWereSaved() {
    transactionsWereSaved(0);
  }
}
