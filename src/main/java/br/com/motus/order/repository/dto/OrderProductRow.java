package br.com.motus.order.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductRow {

    private String productId;
    private String code;
    private String name;
    private BigDecimal price;
}
