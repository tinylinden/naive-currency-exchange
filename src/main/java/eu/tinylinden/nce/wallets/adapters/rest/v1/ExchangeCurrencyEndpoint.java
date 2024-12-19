package eu.tinylinden.nce.wallets.adapters.rest.v1;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import eu.tinylinden.nce.commons.model.CurrencyCode;
import eu.tinylinden.nce.commons.model.CustomerNo;
import eu.tinylinden.nce.commons.usecases.UseCaseExecutor;
import eu.tinylinden.nce.wallets.core.model.CurrencyExchange;
import eu.tinylinden.nce.wallets.core.model.TransactionRef;
import eu.tinylinden.nce.wallets.core.usecases.ExchangeCurrencyUseCase;

import java.math.BigDecimal;
import java.security.Principal;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
class ExchangeCurrencyEndpoint {

  private final UseCaseExecutor<ResponseEntity<?>> useCaseExecutor;
  private final ExchangeCurrencyUseCase useCase;

  @PostMapping(
      path = "/api/v1/exchanges",
      produces = APPLICATION_JSON_VALUE,
      consumes = APPLICATION_JSON_VALUE)
  ResponseEntity<?> exchangeCurrency(
      @RequestHeader("X-Transaction-Ref") String ref,
      @RequestBody Request request,
      Principal principal) {
    return useCaseExecutor.execute(useCase, () -> input(ref, request, principal), this::ok);
  }

  private ExchangeCurrencyUseCase.NewCurrencyExchange input(
      String ref, Request request, Principal principal) {
    return new ExchangeCurrencyUseCase.NewCurrencyExchange()
        .setOwner(CustomerNo.parse(principal.getName()))
        .setRef(TransactionRef.parse(ref))
        .setOperation(CurrencyExchange.Operation.valueOf(request.getOperation()))
        .setSource(CurrencyCode.valueOf(request.getSource()))
        .setTarget(CurrencyCode.valueOf(request.getTarget()))
        .setAmount(request.getAmount());
  }

  private ResponseEntity<CurrencyExchangeDto> ok(CurrencyExchange exchange) {
    return ResponseEntity.ok(CurrencyExchangeDto.fromCurrencyExchange(exchange));
  }

  @Data
  static class Request {
    private String operation;
    private String source;
    private String target;
    private BigDecimal amount;
  }
}
