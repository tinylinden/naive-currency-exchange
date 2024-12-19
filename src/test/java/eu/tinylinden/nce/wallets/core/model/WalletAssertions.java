package eu.tinylinden.nce.wallets.core.model;

import eu.tinylinden.nce.wallets.core.usecases.OpenWalletUseCase;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class WalletAssertions extends AbstractAssert<WalletAssertions, Wallet> {
  private WalletAssertions(Wallet actual) {
    super(actual, WalletAssertions.class);
  }

  public static WalletAssertions assertThat(Wallet actual) {
    return new WalletAssertions(actual);
  }

  public WalletAssertions hasWalletId() {
    Assertions.assertThat(actual.getId()).isNotNull();
    return this;
  }

  public WalletAssertions matches(OpenWalletUseCase.NewWallet given) {
    Assertions.assertThat(actual.getOwner()).isEqualTo(given.getOwner());
    Assertions.assertThat(actual.getCurrency()).isEqualTo(given.getSeed().getCurrency());
    Assertions.assertThat(actual.getBalance()).isEqualTo(given.getSeed().getValue());
    return this;
  }
}
