package com.ishan.reactivesystem.orderservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ishan.reactivesystem.orderservice.controller.PurchaseOrderRequest;
import java.io.IOException;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.rabbitmq.Receiver;

@Service
public class OrderConsumer {

  @Autowired
  private Receiver receiver;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private OrderService orderService;

  @PostConstruct
  public void initConsumer() {
    Flux<PurchaseOrderRequest> messages = receiver
        .consumeAutoAck("order-queue")
        .map(delivery -> {
          try {
            return objectMapper.readValue(delivery.getBody(), PurchaseOrderRequest.class);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });

    orderService.placeOrders(messages)
        .subscribe();

  }

}
