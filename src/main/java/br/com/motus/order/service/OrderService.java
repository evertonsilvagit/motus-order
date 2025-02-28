package br.com.motus.order.service;

import br.com.motus.order.controller.dto.order.OrderProductResponseDTO;
import br.com.motus.order.controller.dto.order.OrderRequestDTO;
import br.com.motus.order.controller.dto.order.OrderResponseDTO;
import br.com.motus.order.model.Order;
import br.com.motus.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public Optional<Order> findById(String id){
        return orderRepository.findById(id);
    }

    @Transactional
    public OrderResponseDTO save(OrderRequestDTO orderRequestDTO){

        Order order = Order.builder()
                .status("Criado")
                .dtCreated(LocalDateTime.now())
                .build();

        Order savedOrder = orderRepository.save(order);

        orderRequestDTO.getProducts()
                        .forEach(productOrderRequestDTO -> {
                            productService.findById(productOrderRequestDTO.getProductId())
                                    .ifPresent(product -> {
                                        orderRepository.saveProductIntoOrder(
                                                savedOrder.getId(),
                                                productOrderRequestDTO.getProductId(),
                                                productOrderRequestDTO.getQuantity());
                                    });
                        });

        List<OrderProductResponseDTO> productResponseDTOS = orderRepository.findOrderWithProducts(savedOrder.getId())
                .stream()
                .map(orderProductRow ->
                        OrderProductResponseDTO.builder()
                                .id(orderProductRow.getProductId())
                                .name(orderProductRow.getName())
                                .code(orderProductRow.getCode())
                                .price(orderProductRow.getPrice())
                                .build()
                ).toList();

        return OrderResponseDTO.builder()
                .id(savedOrder.getId())
                .status(savedOrder.getStatus())
                .dtCreated(savedOrder.getDtCreated())
                .dtUpdated(savedOrder.getDtUpdated())
                .products(productResponseDTOS)
                .build();

    }
}
