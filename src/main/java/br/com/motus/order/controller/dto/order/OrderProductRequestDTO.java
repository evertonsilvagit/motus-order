package br.com.motus.order.controller.dto.order;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderProductRequestDTO {

  private String productId;
  private int quantity;

}
