package eu.tinylinden.nce;

import java.util.Map;
import java.util.function.Supplier;

public interface Trait {
  Map<Class<?>, Object> dependencies();

  @SuppressWarnings("unchecked")
  default <T> T dependency(Class<T> clazz, Supplier<T> supplier) {
    return (T) dependencies().computeIfAbsent(clazz, it -> supplier.get());
  }
}
