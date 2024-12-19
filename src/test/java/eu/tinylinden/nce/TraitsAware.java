package eu.tinylinden.nce;

import java.util.HashMap;
import java.util.Map;

public abstract class TraitsAware implements Trait {
  private final Map<Class<?>, Object> dependencies = new HashMap<>();

  @Override
  public Map<Class<?>, Object> dependencies() {
    return dependencies;
  }
}
