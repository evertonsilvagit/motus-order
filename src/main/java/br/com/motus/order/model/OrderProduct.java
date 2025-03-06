package br.com.motus.order.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tb_order_product")
@Data
@IdClass(OrderProductId.class)
public class OrderProduct {

  @Id
  private String orderId;

  @Id
  private String productId;
}
