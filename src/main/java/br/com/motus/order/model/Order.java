package br.com.motus.order.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tb_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String status;

    @OneToMany(mappedBy = "order")
    private List<Product> products;

    private LocalDateTime dtCreated;
    private LocalDateTime dtUpdated;


}