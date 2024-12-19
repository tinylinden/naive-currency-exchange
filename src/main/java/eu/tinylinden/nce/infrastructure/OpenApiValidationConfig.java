package eu.tinylinden.nce.infrastructure;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.atlassian.oai.validator.springmvc.OpenApiValidationFilter;
import com.atlassian.oai.validator.springmvc.OpenApiValidationInterceptor;
import jakarta.servlet.Filter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class OpenApiValidationConfig {

  @Bean
  Filter validationFilter() {
    return new OpenApiValidationFilter(
        true, // enable request validation
        false // enable response validation
        );
  }

  @Bean
  @SneakyThrows
  WebMvcConfigurer addOpenApiValidationInterceptor(
      @Value("classpath:openapi/nce-v1.yaml") final Resource spec) {
    final var interceptor = new OpenApiValidationInterceptor(new EncodedResource(spec, UTF_8));
    return new WebMvcConfigurer() {
      @Override
      public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(interceptor);
      }
    };
  }
}
