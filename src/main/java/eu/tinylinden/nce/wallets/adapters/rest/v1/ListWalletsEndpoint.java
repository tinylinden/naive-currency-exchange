package eu.tinylinden.nce.wallets.adapters.rest.v1;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import eu.tinylinden.nce.commons.model.CustomerNo;
import eu.tinylinden.nce.commons.usecases.UseCaseExecutor;
import eu.tinylinden.nce.wallets.core.model.Wallet;
import eu.tinylinden.nce.wallets.core.usecases.ListWalletsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
class ListWalletsEndpoint {

  private final UseCaseExecutor<ResponseEntity<?>> useCaseExecutor;
  private final ListWalletsUseCase useCase;

  @GetMapping(path = "/api/v1/wallets", produces = APPLICATION_JSON_VALUE)
  ResponseEntity<?> listWallets(Principal principal) {
    return useCaseExecutor.execute(useCase, () -> input(principal), this::ok);
  }

  private CustomerNo input(Principal principal) {
    return CustomerNo.parse(principal.getName());
  }

  private ResponseEntity<List<WalletDto>> ok(List<Wallet> wallets) {
    return ResponseEntity.ok(wallets.stream().map(WalletDto::fromWallet).toList());
  }
}
