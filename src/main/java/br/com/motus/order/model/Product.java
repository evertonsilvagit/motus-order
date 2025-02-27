package br.com.motus.order.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tb_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String code;
    private String name;
    private BigDecimal price;

    private LocalDateTime dtCreated;
    private LocalDateTime dtUpdated;

}
