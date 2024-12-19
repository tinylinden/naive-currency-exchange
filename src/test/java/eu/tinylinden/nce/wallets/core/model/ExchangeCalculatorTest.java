package eu.tinylinden.nce.wallets.core.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class ExchangeCalculatorTest {

  @Test
  void shouldInvertRate() {
    // given
    var tested = tested(true);

    // when
    var actual = tested.calculate(BigDecimal.ONE);

    // then -- 1 / rate * input
    assertThat(actual).usingComparator(BigDecimal::compareTo).isEqualTo(BigDecimal.valueOf(0.25));
  }

  @Test
  void shouldNotInvertRate() {
    // given
    var tested = tested(false);

    // when
    var actual = tested.calculate(BigDecimal.ONE);

    // then -- // then -- rate * input
    assertThat(actual).usingComparator(BigDecimal::compareTo).isEqualTo(BigDecimal.valueOf(4.02));
  }

  private ExchangeCalculator tested(Boolean invert) {
    return new ExchangeCalculator(new ExchangeRate(BigDecimal.valueOf(4.0228), "TES"), invert);
  }
}
