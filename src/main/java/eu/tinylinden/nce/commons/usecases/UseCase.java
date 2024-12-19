package eu.tinylinden.nce.commons.usecases;

@FunctionalInterface
public interface UseCase<I, O> {
  O execute(I input);
}
