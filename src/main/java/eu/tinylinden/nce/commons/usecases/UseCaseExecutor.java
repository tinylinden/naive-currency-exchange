package eu.tinylinden.nce.commons.usecases;

import java.util.function.Function;
import java.util.function.Supplier;

@FunctionalInterface
public interface UseCaseExecutor<R> {

  <I, O> R execute(UseCase<I, O> useCase, Supplier<I> input, Function<O, R> finalizer);
}
