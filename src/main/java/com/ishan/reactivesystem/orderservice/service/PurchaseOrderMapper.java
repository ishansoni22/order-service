package com.ishan.reactivesystem.orderservice.service;

import com.ishan.reactivesystem.orderservice.controller.PurchaseOrderResponse;
import com.ishan.reactivesystem.orderservice.dao.OrderStatus;
import com.ishan.reactivesystem.orderservice.dao.PurchaseOrder;

public final class PurchaseOrderMapper {

  private PurchaseOrderMapper() {

  }

  public static PurchaseOrder create(OrderRef orderRef) {
    PurchaseOrder purchaseOrder = new PurchaseOrder();

    purchaseOrder.setProductId(orderRef.getProductRef().getUuid());
    purchaseOrder.setUserId(orderRef.getOrderRequestRef().getUserId());
    purchaseOrder.setAmount(orderRef.getProductRef().getPrice());

    String transactionStatus = orderRef.getTransactionRef().getStatus();
    OrderStatus orderStatus =
        "SUCCESS".equalsIgnoreCase(transactionStatus) ? OrderStatus.FULFILLED : OrderStatus.FAILED;

    purchaseOrder.setStatus(orderStatus);

    return purchaseOrder;

  }

  public static PurchaseOrderResponse toPurchaseOrderResponse(PurchaseOrder purchaseOrder) {
    PurchaseOrderResponse purchaseOrderResponse = new PurchaseOrderResponse();
    purchaseOrderResponse.setId(purchaseOrder.getId());
    purchaseOrderResponse.setProductId(purchaseOrder.getProductId());
    purchaseOrderResponse.setUserId(purchaseOrder.getUserId());
    purchaseOrderResponse.setAmount(purchaseOrder.getAmount());
    purchaseOrderResponse.setStatus(purchaseOrder.getStatus());

    return purchaseOrderResponse;
  }

}
