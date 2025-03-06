package br.com.motus.order.controller.dto.order;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductResponseDTO {

  private String id;
  private String code;
  private String name;
  private BigDecimal price;
  private Integer quantity;

}
