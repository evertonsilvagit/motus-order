package br.com.motus.order.controller.dto.order;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponseDTO {

    private String id;
    private String status;
    private List<OrderProductResponseDTO> products;
    private LocalDateTime dtCreated;
    private LocalDateTime dtUpdated;

}