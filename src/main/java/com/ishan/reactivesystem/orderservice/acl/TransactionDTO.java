package com.ishan.reactivesystem.orderservice.acl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDTO {

  private Long id;
  private Long userId;
  private LocalDateTime time;
  private String status;
  private BigDecimal amount;
  private String reference;

}
