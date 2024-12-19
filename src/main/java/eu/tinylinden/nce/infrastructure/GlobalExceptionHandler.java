package eu.tinylinden.nce.infrastructure;

import com.atlassian.oai.validator.springmvc.InvalidRequestException;
import eu.tinylinden.nce.commons.exceptions.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class GlobalExceptionHandler {

  @ExceptionHandler
  ResponseEntity<ErrorDto> badRequest(InvalidRequestException ex) {
    return ResponseEntity.badRequest()
        .body(
            new ErrorDto()
                .setCode("BadRequest")
                .setMessage(ex.getValidationReport().getMessages().toString()));
  }
}
