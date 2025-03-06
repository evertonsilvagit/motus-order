package br.com.motus.order.controller.dto.order;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderWithTotalPriceDTO {

  private String orderId;
  private BigDecimal totalPrice;

}
