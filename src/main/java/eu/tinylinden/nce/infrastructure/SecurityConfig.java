package eu.tinylinden.nce.infrastructure;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import eu.tinylinden.nce.commons.model.CustomerNo;
import eu.tinylinden.nce.customers.core.model.Customer;
import eu.tinylinden.nce.customers.core.ports.CustomerFinder;
import java.util.Collections;

import eu.tinylinden.nce.customers.core.ports.PassEncoder;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

@Configuration
class SecurityConfig {

  @Bean
  @SneakyThrows
  SecurityFilterChain securityFilterChain(HttpSecurity http) {
    return http.csrf(it -> it.disable())
        .cors(it -> {}) // todo: investigate why disabling does not work
        .authorizeHttpRequests(
            it ->
                it.requestMatchers(POST, "/api/v1/customers")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .sessionManagement(it -> it.sessionCreationPolicy(STATELESS))
        .httpBasic(it -> it.authenticationEntryPoint(new Http403ForbiddenEntryPoint()))
        .build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  PassEncoder passEncoder(PasswordEncoder delegate) {
    return delegate::encode;
  }

  @Bean
  UserDetailsService userDetailsService(CustomerFinder customerFinder) {
    return new UserDetailsService() {
      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var customer = customerFinder.find(CustomerNo.parse(username));
        return customer.map(this::user).orElseThrow(() -> new UsernameNotFoundException(username));
      }

      private UserDetails user(Customer customer) {
        return new User(
            customer.getCustomerNo().getString(), customer.getPassword(), Collections.emptyList());
      }
    };
  }
}
