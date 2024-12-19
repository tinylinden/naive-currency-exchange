package eu.tinylinden.nce.commons.model;

import com.github.f4b6a3.tsid.TsidCreator;
import lombok.Value;

@Value
public class WalletId implements UniqueId {
  Long raw;

  public static WalletId next() {
    return new WalletId(TsidCreator.getTsid().toLong());
  }

  public static WalletId parse(String string) {
    return new WalletId(Long.parseLong(string));
  }
}
