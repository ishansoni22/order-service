package com.ishan.reactivesystem.orderservice.controller;

import com.ishan.reactivesystem.orderservice.service.OrderPublisher;
import com.ishan.reactivesystem.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/orders")
public class PurchaseOrderController {

  @Autowired
  private OrderService orderService;

  @Autowired
  private OrderPublisher orderPublisher;

  @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Flux<PurchaseOrderResponse> getOrdersForUser(@PathVariable("id") Long userId) {
    return orderService.getOrdersForUser(userId);
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<PurchaseOrderResponse>> placeOrder(
      @RequestBody Mono<PurchaseOrderRequest> orderRequest) {
    return orderService.placeSingleOrder(orderRequest)
        .map(ResponseEntity::ok)
        .onErrorReturn(WebClientResponseException.class, ResponseEntity.badRequest().build())
        .onErrorReturn(WebClientRequestException.class,
            ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
  }

  @PostMapping(value = "/publish", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<Void> publishOrder(
      @RequestBody Mono<PurchaseOrderRequest> orderRequest) {
    return orderPublisher.publishOrder(orderRequest);
  }

}
