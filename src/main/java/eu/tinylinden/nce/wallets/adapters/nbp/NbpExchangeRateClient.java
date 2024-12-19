package eu.tinylinden.nce.wallets.adapters.nbp;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "nbpExchangeRateClient", url = "${nce.adapters.nbp.url}")
interface NbpExchangeRateClient {

  @GetMapping(path = "/api/exchangerates/rates/c/{currency}", consumes = APPLICATION_JSON_VALUE)
  NbpExchangeRatesDto getRates(@PathVariable("currency") String currency);
}
