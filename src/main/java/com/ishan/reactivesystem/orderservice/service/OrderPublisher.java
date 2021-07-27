package com.ishan.reactivesystem.orderservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ishan.reactivesystem.orderservice.controller.PurchaseOrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Sender;

@Service
@Slf4j
public class OrderPublisher {

  @Autowired
  private Sender sender;

  @Autowired
  private ObjectMapper objectMapper;

  public Mono<Void> publishOrder(Mono<PurchaseOrderRequest> purchaseOrderRequest) {
    Mono<OutboundMessage> orderMessage = purchaseOrderRequest
        .map(or -> {
          try {
            return new OutboundMessage("order-exchange", "order",
                objectMapper.writeValueAsBytes(or));
          } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
          }
        });

    return sender.send(orderMessage)
        .doOnError(e -> log.error("Cannot publish order request", e))
        .then();
  }

}
