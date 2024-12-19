package eu.tinylinden.nce.infrastructure;

import eu.tinylinden.nce.commons.exceptions.DomainException;
import eu.tinylinden.nce.commons.exceptions.ErrorDto;
import eu.tinylinden.nce.commons.usecases.UseCase;
import eu.tinylinden.nce.commons.usecases.UseCaseExecutor;
import java.util.function.Function;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class RestControllerUseCaseExecutor implements UseCaseExecutor<ResponseEntity<?>> {

  @Override
  public <I, O> ResponseEntity<?> execute(
      UseCase<I, O> useCase, Supplier<I> input, Function<O, ResponseEntity<?>> finalizer) {
    try {
      log.debug("Executing {}", name(useCase));
      var result = useCase.execute(input.get());
      return finalizer.apply(result);
    } catch (DomainException ex) {
      log.warn("Problem with {} - {}", name(useCase), ex.getCode());
      var locale = LocaleContextHolder.getLocale();
      return errorResponse(ex.setLocale(locale));
    } catch (Exception ex) {
      log.error("Panic", ex);
      var error = new ErrorDto().setCode("Panic").setMessage(ex.getMessage());
      return ResponseEntity.internalServerError().body(error);
    }
  }

  private String name(UseCase<?, ?> useCase) {
    return useCase.getClass().getSimpleName().replaceAll("\\$.*", "");
  }

  private ResponseEntity<?> errorResponse(final DomainException ex) {
    var status = Integer.parseInt(ex.getString("httpStatus", "500"));
    return ResponseEntity.status(status).body(ErrorDto.fromDomainException(ex));
  }
}
