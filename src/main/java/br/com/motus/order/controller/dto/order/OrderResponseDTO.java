package br.com.motus.order.controller.dto.order;

import br.com.motus.order.controller.dto.product.ProductResponseDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponseDTO {
    private String id;
    private List<ProductResponseDTO> products;
    private String status;
    private LocalDateTime dtCreated;
    private LocalDateTime dtUpdated;
}