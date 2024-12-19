package eu.tinylinden.nce.wallets.adapters.rest.v1;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import eu.tinylinden.nce.commons.model.CustomerNo;
import eu.tinylinden.nce.commons.model.WalletId;
import eu.tinylinden.nce.commons.usecases.UseCaseExecutor;
import eu.tinylinden.nce.wallets.core.model.Transaction;
import eu.tinylinden.nce.wallets.core.usecases.ListTransactionsUseCase;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
class ListTransactionsEndpoint {

  private final UseCaseExecutor<ResponseEntity<?>> useCaseExecutor;
  private final ListTransactionsUseCase useCase;

  @GetMapping(path = "/api/v1/wallets/{walletId}/transactions", produces = APPLICATION_JSON_VALUE)
  ResponseEntity<?> listTransactions(@PathVariable("walletId") String walletId, Principal principal) {
    return useCaseExecutor.execute(useCase, () -> input(walletId, principal), this::ok);
  }

  private ListTransactionsUseCase.Input input(String walletId, Principal principal) {
    return new ListTransactionsUseCase.Input(
        CustomerNo.parse(principal.getName()), WalletId.parse(walletId));
  }

  private ResponseEntity<List<TransactionDto>> ok(List<Transaction> transactions) {
    return ResponseEntity.ok(transactions.stream().map(TransactionDto::fromTransaction).toList());
  }
}
