package br.com.motus.order.service;

import br.com.motus.order.model.Order;
import br.com.motus.order.model.Product;
import br.com.motus.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, ProductService productService){
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    public List<Order> list(){
        return orderRepository.findAll();
    }

    public Order save(Order order, List<String> productIds){
        List<Product> products = productService.findAllByIds(productIds);
        order.setStatus("Criado");
        order.setProducts(products);
        return orderRepository.save(order);
    }
}
