package com.ishan.reactivesystem.orderservice.service;

import com.ishan.reactivesystem.orderservice.acl.ProductDTO;
import com.ishan.reactivesystem.orderservice.acl.TransactionDTO;
import com.ishan.reactivesystem.orderservice.controller.PurchaseOrderRequest;
import com.ishan.reactivesystem.orderservice.controller.PurchaseOrderResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class OrderRef {

  private final PurchaseOrderRequest orderRequestRef;

  private ProductDTO productRef;

  private TransactionDTO transactionRef;

  private PurchaseOrderResponse orderResponseRef;

  OrderRef(PurchaseOrderRequest orderRequestRef) {
    this.orderRequestRef = orderRequestRef;
  }

}
