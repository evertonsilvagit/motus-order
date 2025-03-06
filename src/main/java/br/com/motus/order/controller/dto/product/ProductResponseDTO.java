package br.com.motus.order.controller.dto.product;


import br.com.motus.order.model.Product;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponseDTO(
    String id,
    String code,
    String name,
    BigDecimal price,
    LocalDateTime dtCreated,
    LocalDateTime dtUpdated
) {

  public ProductResponseDTO(Product product) {
    this(
        product.getId(),
        product.getCode(),
        product.getName(),
        product.getPrice(),
        product.getDtCreated(),
        product.getDtUpdated()
    );
  }

}
