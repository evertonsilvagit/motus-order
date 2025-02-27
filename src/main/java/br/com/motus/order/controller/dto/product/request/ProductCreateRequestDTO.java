package br.com.motus.order.controller.dto.product.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateRequestDTO {

    private String code;
    private String name;
    private BigDecimal price;

}
