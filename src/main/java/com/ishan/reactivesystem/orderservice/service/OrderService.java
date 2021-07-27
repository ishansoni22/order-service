package com.ishan.reactivesystem.orderservice.service;

import com.ishan.reactivesystem.orderservice.acl.ProductService;
import com.ishan.reactivesystem.orderservice.acl.UserService;
import com.ishan.reactivesystem.orderservice.controller.PurchaseOrderRequest;
import com.ishan.reactivesystem.orderservice.controller.PurchaseOrderResponse;
import com.ishan.reactivesystem.orderservice.dao.PurchaseOrderRepository;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Service
public class OrderService {

  @Autowired
  private ProductService productService;

  @Autowired
  private UserService userService;

  @Autowired
  private PurchaseOrderRepository purchaseOrderRepository;

  public Flux<PurchaseOrderResponse> getOrdersForUser(Long userId) {
    return purchaseOrderRepository
        .findByUserId(userId)
        .map(PurchaseOrderMapper::toPurchaseOrderResponse);
  }

  public Mono<PurchaseOrderResponse> placeSingleOrder(Mono<PurchaseOrderRequest> orderRequest) {
    return orderRequest
        .log()
        .map(OrderRef::new)
        .flatMap(this::getProduct)
        .flatMap(this::transact)
        .map(PurchaseOrderMapper::create)
        .flatMap(purchaseOrderRepository::save)
        .map(PurchaseOrderMapper::toPurchaseOrderResponse);
  }

  public Flux<PurchaseOrderResponse> placeOrders(Flux<PurchaseOrderRequest> orderRequest) {
    return orderRequest
        .log()
        .map(OrderRef::new)
        .flatMap(this::getProduct)
        .flatMap(this::transact)
        .map(PurchaseOrderMapper::create)
        .flatMap(purchaseOrderRepository::save)
        .map(PurchaseOrderMapper::toPurchaseOrderResponse);
  }

  private Mono<OrderRef> getProduct(OrderRef orderRef) {
    return this.productService
        .get(orderRef.getOrderRequestRef().getProductId())
        //Retry a maximum of 5 times
        //.retry(5)
        //Retry a maximum of 5 times with a delay of 2 seconds b/w each requests
        //.retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(2)))
        //Retry a maximum of 5 times with exponential backoff? (First backoff duration is 2 seconds)
        .retryWhen(Retry.backoff(5, Duration.ofSeconds(2)))
        .doOnNext(orderRef::setProductRef)
        .thenReturn(orderRef);
  }

  private Mono<OrderRef> transact(OrderRef orderRef) {
    return this.userService
        .transact(
            orderRef.getOrderRequestRef().getUserId(),
            orderRef.getProductRef().getPrice(),
            "order-" + orderRef.getProductRef().getDescription()
        ).doOnNext(orderRef::setTransactionRef)
        .thenReturn(orderRef);
  }

}
