package com.ishan.reactivesystem.orderservice.controller;

import com.ishan.reactivesystem.orderservice.dao.OrderStatus;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PurchaseOrderResponse {

  private Long id;
  private Long userId;
  private String productId;
  private BigDecimal amount;
  private OrderStatus status;

}
