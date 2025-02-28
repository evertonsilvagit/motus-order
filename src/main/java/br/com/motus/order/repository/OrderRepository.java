package br.com.motus.order.repository;

import br.com.motus.order.model.Order;
import br.com.motus.order.repository.dto.OrderProductRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {

    @Modifying
    @Query(value = "INSERT INTO tb_order_product (order_id, product_id, quantity) VALUES (:productId, :orderId, :quantity)", nativeQuery = true)
    void saveProductIntoOrder(String productId, String orderId, Integer quantity);

    @Query(value = """
            SELECT distinct
                top.product_id as productId,
                tp.code,
                tp.name,
                tp.price,
                top.quantity
            FROM tb_order_product top
            INNER JOIN tb_product as tp on top.product_id = tp.id
            WHERE top.order_id = :orderId
    """,
    nativeQuery = true)
    List<OrderProductRow> findOrderWithProducts(String orderId);

}
