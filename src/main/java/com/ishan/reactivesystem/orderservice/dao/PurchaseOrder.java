package com.ishan.reactivesystem.orderservice.dao;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class PurchaseOrder {

  @Id
  private Long id;
  private Long userId;
  private String productId;
  private BigDecimal amount;
  private OrderStatus status;

}
