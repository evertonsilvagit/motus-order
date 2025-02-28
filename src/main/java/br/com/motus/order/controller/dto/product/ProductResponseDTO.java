package br.com.motus.order.controller.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {

    private String id;
    private String code;
    private String name;
    private BigDecimal price;
    private LocalDateTime dtCreated;
    private LocalDateTime dtUpdated;

}
