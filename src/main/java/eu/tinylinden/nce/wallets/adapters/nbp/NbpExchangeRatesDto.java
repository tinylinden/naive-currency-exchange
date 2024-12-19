package eu.tinylinden.nce.wallets.adapters.nbp;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
class NbpExchangeRatesDto {
  List<NbpExchangeRateDto> rates;
}
