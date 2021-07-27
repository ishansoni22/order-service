package com.ishan.reactivesystem.orderservice.acl;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

  private String uuid;

  private String description;

  private BigDecimal price;

}
