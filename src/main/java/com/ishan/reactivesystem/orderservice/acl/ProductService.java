package com.ishan.reactivesystem.orderservice.acl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

  @Autowired
  private WebClient productWebClient;

  public Mono<ProductDTO> get(String id) {

    return productWebClient
        .get()
        .uri("/v1/products/{id}", id)
        .retrieve()
        .bodyToMono(ProductDTO.class);
  }

}
