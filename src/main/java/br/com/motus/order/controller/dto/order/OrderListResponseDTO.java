package br.com.motus.order.controller.dto.order;

import br.com.motus.order.model.Order;
import java.time.LocalDateTime;

public record OrderListResponseDTO(
    String id,
    String status,
    LocalDateTime dtCreated,
    LocalDateTime dtUpdated
) {

  public OrderListResponseDTO(Order order) {
    this(
        order.getId(),
        order.getStatus(),
        order.getDtCreated(),
        order.getDtUpdated()
    );
  }

}