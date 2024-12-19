package eu.tinylinden.nce.wallets.core.ports;

import eu.tinylinden.nce.wallets.core.model.Transaction;

public interface TransactionRepository extends TransactionFinder {
  Transaction save(Transaction transaction);
}
