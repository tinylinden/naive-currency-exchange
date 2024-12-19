package eu.tinylinden.nce.commons.exceptions;

import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.text.StringSubstitutor;

@Data
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(
    of = {"code"},
    callSuper = false)
public class DomainException extends RuntimeException {
  private final String code;
  private final Map<String, Object> context;
  private Locale locale;

  public String message() {
    return getString("message", "");
  }

  public String userMessage() {
    return getString("userMessage", "");
  }

  public String getString(String key, String fallback) {
    try {
      var effectiveKey = code + "." + key;
      var raw = ResourceBundle.getBundle("i18n.DomainException", locale).getString(effectiveKey);
      return StringSubstitutor.replace(raw, context);
    } catch (Exception ex) {
      return fallback;
    }
  }

  public static DomainException walletNotFound() {
    return new DomainException("WalletNotFound", Collections.emptyMap(), Locale.getDefault());
  }
}
