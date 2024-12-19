package eu.tinylinden.nce.customers.adapters.rest.v1;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import eu.tinylinden.nce.commons.usecases.UseCaseExecutor;
import eu.tinylinden.nce.customers.core.model.Customer;
import eu.tinylinden.nce.customers.core.usecases.RegisterCustomerUseCase;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
class RegisterCustomerEndpoint {

  private final UseCaseExecutor<ResponseEntity<?>> useCaseExecutor;
  private final RegisterCustomerUseCase useCase;

  @PostMapping(
      path = "/api/v1/customers",
      produces = APPLICATION_JSON_VALUE,
      consumes = APPLICATION_JSON_VALUE)
  ResponseEntity<?> registerCustomer(@RequestBody Request request) {
    return useCaseExecutor.execute(useCase, () -> input(request), this::ok);
  }

  private RegisterCustomerUseCase.NewCustomer input(Request request) {
    return new RegisterCustomerUseCase.NewCustomer()
        .setFirstName(request.getFirstName())
        .setLastName(request.getLastName())
        .setPassword(request.getPassword());
  }

  private ResponseEntity<CustomerDto> ok(Customer customer) {
    return ResponseEntity.ok(CustomerDto.fromCustomer(customer));
  }

  @Data
  @Accessors(chain = true)
  static class Request {
    private String firstName;
    private String lastName;
    private String password;
  }
}
