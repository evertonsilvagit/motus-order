package br.com.motus.order.controller.dto.product;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ProductRequestDTO(
    @NotBlank(message = "code cannot be null")
    String code,
    @NotBlank(message = "name cannot be null")
    String name,
    @NotNull(message = "price cannot be null")
    @Digits(integer = 7, fraction = 2, message = "price must have a maximum of 7 digits and 2 decimal places")
    BigDecimal price
) {}
