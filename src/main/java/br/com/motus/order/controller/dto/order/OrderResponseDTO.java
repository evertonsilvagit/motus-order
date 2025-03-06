package br.com.motus.order.controller.dto.order;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponseDTO {

  private String id;
  private String status;
  private List<OrderProductResponseDTO> products;
  private LocalDateTime dtCreated;
  private LocalDateTime dtUpdated;

}