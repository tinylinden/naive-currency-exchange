package eu.tinylinden.nce.wallets.core.usecases;

import eu.tinylinden.nce.commons.exceptions.DomainException;
import eu.tinylinden.nce.commons.model.CustomerNo;
import eu.tinylinden.nce.commons.model.WalletId;
import eu.tinylinden.nce.commons.usecases.UseCase;
import eu.tinylinden.nce.wallets.core.model.Transaction;
import eu.tinylinden.nce.wallets.core.ports.TransactionFinder;
import eu.tinylinden.nce.wallets.core.ports.WalletFinder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ListTransactionsUseCase
    implements UseCase<ListTransactionsUseCase.Input, List<Transaction>> {

  private final WalletFinder wallets;
  private final TransactionFinder transactions;

  @Override
  public List<Transaction> execute(final Input input) {
    log.debug("Listing transactions for {}", input.getWallet().getString());
    checkOwnership(input);
    return transactions.list(input.wallet);
  }

  private void checkOwnership(final Input input) {
    wallets.find(input.walletSpec()).orElseThrow(DomainException::walletNotFound);
  }

  @Value
  public static class Input {
    CustomerNo owner;
    WalletId wallet;

    WalletFinder.Specification walletSpec() {
      return new WalletFinder.Specification.ByOwnerAndId(owner, wallet);
    }
  }
}
