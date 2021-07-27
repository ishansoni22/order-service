package com.ishan.reactivesystem.orderservice.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PurchaseOrderRequest {

  private Long userId;
  private String productId;

}
