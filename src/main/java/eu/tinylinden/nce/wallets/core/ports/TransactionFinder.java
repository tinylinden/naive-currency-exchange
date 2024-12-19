package eu.tinylinden.nce.wallets.core.ports;

import eu.tinylinden.nce.commons.model.WalletId;
import eu.tinylinden.nce.wallets.core.model.Transaction;
import eu.tinylinden.nce.wallets.core.model.TransactionRef;
import lombok.Value;

import java.util.List;

public interface TransactionFinder {
  List<Transaction> list(WalletId wallet);

  List<Transaction> list(Specification specification);

  interface Specification {

    @Value
    class ByRef implements Specification {
      TransactionRef ref;
    }
  }
}
