package com.quintrix.jepsen.erik.sixth.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SixthClient {
  private Resilience4JCircuitBreakerFactory circuitBreakerFactory;
  private RestTemplate restTemplate;
  @Value("${sixth.maxTimeout}")
  private int maxTimeout;

  public SixthClient(Resilience4JCircuitBreakerFactory circuitBreakerFactory) {
    this.circuitBreakerFactory = circuitBreakerFactory;
    restTemplate = new RestTemplate();
    restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
  }

  public <T> T getForObject(String uri, Class<T> responseType, T thrown) {
    CircuitBreaker circuitBreaker = circuitBreakerFactory.create("default");
    return (T) circuitBreaker.run(() -> restTemplate.getForObject(uri, responseType),
        throwable -> thrown);
  }

  public RestTemplate getRestTemplate() {
    return restTemplate;
  }
}
