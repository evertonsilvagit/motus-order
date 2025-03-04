package br.com.motus.order.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_order_product")
@IdClass(OrderProductId.class)
public class OrderProduct {

    @Id
    private String orderId;

    @Id
    private String productId;
}
