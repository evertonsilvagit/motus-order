package br.com.motus.order.repository;

import br.com.motus.order.model.OrderProduct;
import br.com.motus.order.model.OrderProductId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {

  Optional<OrderProduct> findByProductId(String productId);
}
