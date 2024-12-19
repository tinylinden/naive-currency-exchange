package eu.tinylinden.nce.commons.exceptions;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ErrorDto {
  private String code;
  private String message;
  private String userMessage;

  public static ErrorDto fromDomainException(DomainException ex) {
    return new ErrorDto()
        .setCode(ex.getCode())
        .setMessage(ex.message())
        .setUserMessage(ex.userMessage());
  }
}
