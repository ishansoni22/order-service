package com.ishan.reactivesystem.orderservice.acl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

  @Value("${product.service.url}")
  private String productServiceURL;

  @Value("${user.service.url}")
  private String userServiceURL;

  @Bean
  public WebClient productWebClient() {
    return WebClient.builder()
        .baseUrl(productServiceURL)
        .build();
  }

  @Bean
  public WebClient userWebClient() {
    return WebClient.builder()
        .baseUrl(userServiceURL)
        .build();
  }

}