package com.quintrix.jepsen.erik.sixth;

import org.springframework.cloud.circuitbreaker.springretry.SpringRetryCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.springretry.SpringRetryConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.policy.TimeoutRetryPolicy;

@Configuration
public class ConfigClass {

  @Bean
  public Customizer<SpringRetryCircuitBreakerFactory> defaultCustomizer() {
    return factory -> factory.configureDefault(
        id -> new SpringRetryConfigBuilder(id).retryPolicy(new TimeoutRetryPolicy()).build());
  }
}
