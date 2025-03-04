package br.com.motus.order.repository;

import br.com.motus.order.model.OrderProduct;
import br.com.motus.order.model.OrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {
    Optional<OrderProduct> findByProductId(String productId);
}
