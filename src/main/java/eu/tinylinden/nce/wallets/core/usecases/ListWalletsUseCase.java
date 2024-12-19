package eu.tinylinden.nce.wallets.core.usecases;

import eu.tinylinden.nce.commons.model.CustomerNo;
import eu.tinylinden.nce.commons.usecases.UseCase;
import eu.tinylinden.nce.wallets.core.model.Wallet;
import eu.tinylinden.nce.wallets.core.ports.WalletFinder;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ListWalletsUseCase implements UseCase<CustomerNo, List<Wallet>> {

  private final WalletFinder wallets;

  @Override
  public List<Wallet> execute(CustomerNo input) {
    log.debug("Listing wallets for {}", input.getString());
    return wallets.list(input);
  }
}
