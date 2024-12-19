package eu.tinylinden.nce.wallets.adapters.nbp;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
class NbpExchangeRateDto {
  private BigDecimal ask;
  private BigDecimal bid;
  private String no;
}
