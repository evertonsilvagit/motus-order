package br.com.motus.order.repository.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductRow {

  private String productId;
  private String code;
  private String name;
  private BigDecimal price;
  private Integer quantity;

}
