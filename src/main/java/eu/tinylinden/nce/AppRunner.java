package eu.tinylinden.nce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AppRunner {

  public static void main(String[] args) {
    SpringApplication.run(AppRunner.class, args);
  }
}
