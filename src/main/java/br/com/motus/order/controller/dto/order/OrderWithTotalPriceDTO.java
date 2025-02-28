package br.com.motus.order.controller.dto.order;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderWithTotalPriceDTO {

    private String orderId;
    private BigDecimal totalPrice;

}
