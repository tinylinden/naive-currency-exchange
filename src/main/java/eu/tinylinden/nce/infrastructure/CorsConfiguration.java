package eu.tinylinden.nce.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
class CorsConfiguration {

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration());
        return source;
    }

    private org.springframework.web.cors.CorsConfiguration corsConfiguration() {
        var all = List.of("*");
        var conf = new org.springframework.web.cors.CorsConfiguration();
        conf.setAllowedOrigins(all);
        conf.setAllowedMethods(all);
        conf.setAllowedHeaders(all);
        return conf;
    }
}
