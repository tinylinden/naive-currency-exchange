package eu.tinylinden.nce.wallets.adapters.rest.v1;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import eu.tinylinden.nce.commons.model.CurrencyCode;
import eu.tinylinden.nce.commons.model.CustomerNo;
import eu.tinylinden.nce.commons.model.MonetaryAmount;
import eu.tinylinden.nce.commons.usecases.UseCaseExecutor;
import eu.tinylinden.nce.wallets.core.model.Wallet;
import eu.tinylinden.nce.wallets.core.usecases.OpenWalletUseCase;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
@CrossOrigin
@RequiredArgsConstructor
class OpenWalletEndpoint {

  private final UseCaseExecutor<ResponseEntity<?>> useCaseExecutor;
  private final OpenWalletUseCase useCase;

  @PostMapping(
      path = "/api/v1/wallets",
      produces = APPLICATION_JSON_VALUE,
      consumes = APPLICATION_JSON_VALUE)
  ResponseEntity<?> openWallet(@RequestBody Request request, Principal principal) {
    return useCaseExecutor.execute(useCase, () -> input(request, principal), this::ok);
  }

  private OpenWalletUseCase.NewWallet input(Request request, Principal principal) {
    return new OpenWalletUseCase.NewWallet()
        .setOwner(CustomerNo.parse(principal.getName()))
        .setSeed(
            new MonetaryAmount(CurrencyCode.valueOf(request.currency), request.initialBalance));
  }

  private ResponseEntity<WalletDto> ok(Wallet wallet) {
    return ResponseEntity.ok(WalletDto.fromWallet(wallet));
  }

  @Data
  static class Request {
    private String currency;
    private BigDecimal initialBalance;
  }
}
