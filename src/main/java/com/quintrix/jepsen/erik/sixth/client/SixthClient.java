package com.quintrix.jepsen.erik.sixth.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.circuitbreaker.springretry.SpringRetryCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.springretry.SpringRetryConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.retry.policy.TimeoutRetryPolicy;
import org.springframework.web.client.RestTemplate;

public class SixthClient {
  private SpringRetryCircuitBreakerFactory circuitBreakerFactory;
  @Autowired
  private RestTemplate restTemplate;
  @Value("${sixth.maxTimeout}")
  private int maxTimeout;

  public SixthClient() {
    restTemplate = new RestTemplate();
    restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    circuitBreakerFactory = new SpringRetryCircuitBreakerFactory();
    circuitBreakerFactory.configureDefault(
        id -> new SpringRetryConfigBuilder(id).retryPolicy(new TimeoutRetryPolicy()).build());
  }

  @SuppressWarnings("unchecked")
  public <T> T getForObject(String uri, Class<T> responseType) {
    CircuitBreaker circuitBreaker = circuitBreakerFactory.create("getUri");
    return (T) circuitBreaker.run(() -> restTemplate.getForObject(uri, responseType),
        throwable -> "fallback");
  }

  public RestTemplate getRestTemplate() {
    return restTemplate;
  }
}
