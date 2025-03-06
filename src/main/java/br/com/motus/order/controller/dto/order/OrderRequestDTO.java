package br.com.motus.order.controller.dto.order;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record OrderRequestDTO(

    @NotEmpty(message = "Product list must not be empty.")
    List<OrderProductRequestDTO> products,
    String status
) {

}