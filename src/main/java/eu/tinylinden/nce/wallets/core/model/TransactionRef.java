package eu.tinylinden.nce.wallets.core.model;

import lombok.Value;

import java.util.UUID;

@Value
public class TransactionRef {
  String raw;

  public static TransactionRef parse(String string) {
    if (string == null) {
      return new TransactionRef(UUID.randomUUID().toString());
    }
    return new TransactionRef(string);
  }
}
