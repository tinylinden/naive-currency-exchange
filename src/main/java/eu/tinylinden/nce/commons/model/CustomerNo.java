package eu.tinylinden.nce.commons.model;

import com.github.f4b6a3.tsid.TsidCreator;
import lombok.Value;

@Value
public class CustomerNo implements UniqueId {
  Long raw;

  public static CustomerNo next() {
    return new CustomerNo(TsidCreator.getTsid().toLong());
  }

  public static CustomerNo parse(String string) {
    return new CustomerNo(Long.parseLong(string));
  }
}
