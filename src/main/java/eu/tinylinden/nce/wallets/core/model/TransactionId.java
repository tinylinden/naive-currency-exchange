package eu.tinylinden.nce.wallets.core.model;

import com.github.f4b6a3.tsid.TsidCreator;
import eu.tinylinden.nce.commons.model.UniqueId;
import lombok.Value;

@Value
public class TransactionId implements UniqueId {
  Long raw;

  public static TransactionId next() {
    return new TransactionId(TsidCreator.getTsid().toLong());
  }

  public static TransactionId parse(String string) {
    return new TransactionId(Long.parseLong(string));
  }
}
