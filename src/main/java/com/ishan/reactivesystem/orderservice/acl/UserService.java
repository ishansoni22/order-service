package com.ishan.reactivesystem.orderservice.acl;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserService {

  @Autowired
  private WebClient userWebClient;

  public Mono<TransactionDTO> transact(Long userId, BigDecimal amount, String reference) {
    TransactionRequest tr = new TransactionRequest();
    tr.setUserId(userId);
    tr.setAmount(amount);
    tr.setReference(reference);

    return userWebClient
        .post()
        .uri("/v1/transactions")
        .bodyValue(tr)
        .retrieve()
        .bodyToMono(TransactionDTO.class);
  }

}
