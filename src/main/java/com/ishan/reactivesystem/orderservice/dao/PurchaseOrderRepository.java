package com.ishan.reactivesystem.orderservice.dao;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PurchaseOrderRepository extends ReactiveCrudRepository<PurchaseOrder, Long> {

  Flux<PurchaseOrder> findByUserId(Long userId);

}
