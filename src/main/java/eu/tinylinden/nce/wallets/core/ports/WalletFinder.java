package eu.tinylinden.nce.wallets.core.ports;

import eu.tinylinden.nce.commons.model.CurrencyCode;
import eu.tinylinden.nce.commons.model.CustomerNo;
import eu.tinylinden.nce.commons.model.WalletId;
import eu.tinylinden.nce.wallets.core.model.Wallet;
import lombok.Value;

import java.util.List;
import java.util.Optional;

public interface WalletFinder {

  List<Wallet> list(CustomerNo owner);

  Optional<Wallet> find(Specification specification);

  interface Specification {

    @Value
    class ByOwnerAndId implements Specification {
      CustomerNo owner;
      WalletId id;
    }

    @Value
    class ByOwnerAndCurrency implements Specification {
      CustomerNo owner;
      CurrencyCode currency;
    }
  }
}
