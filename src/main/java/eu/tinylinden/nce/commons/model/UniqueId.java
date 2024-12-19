package eu.tinylinden.nce.commons.model;

public interface UniqueId {
  Long getRaw();

  default String getString() {
    return getRaw().toString();
  }
}
