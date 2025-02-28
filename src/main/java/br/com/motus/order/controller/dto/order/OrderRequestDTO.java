package br.com.motus.order.controller.dto.order;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderRequestDTO {
    private List<OrderProductRequestDTO> products;
    private String status;
}