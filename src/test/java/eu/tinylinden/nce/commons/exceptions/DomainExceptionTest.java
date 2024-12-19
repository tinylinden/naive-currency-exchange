package eu.tinylinden.nce.commons.exceptions;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.util.Collections;
import java.util.Locale;
import org.junit.jupiter.api.Test;

class DomainExceptionTest {

  @Test
  void shouldProvideAllInfo() {
    // given
    var tested = DomainException.walletNotFound();

    // expect
    assertThat(tested.getCode()).isNotBlank();
    assertThat(tested.message()).isNotBlank();
    assertThat(tested.userMessage()).isNotBlank();
  }

  @Test
  void shouldRespectLocale() {
    // given
    var tested =
        new DomainException(
            "WalletNotFound", Collections.emptyMap(), Locale.forLanguageTag("pl-PL"));

    // expect
    assertThat(tested.userMessage())
        .isEqualTo("Wskazany portfel nie istnieje albo nie należy do Ciebie.");
  }
}
