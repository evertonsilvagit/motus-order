package br.com.motus.order.service;

import br.com.motus.order.controller.dto.order.OrderProductResponseDTO;
import br.com.motus.order.controller.dto.order.OrderRequestDTO;
import br.com.motus.order.controller.dto.order.OrderResponseDTO;
import br.com.motus.order.controller.dto.order.OrderWithTotalPriceDTO;
import br.com.motus.order.exception.CantCreateOrderProductDoesntExistException;
import br.com.motus.order.model.Order;
import br.com.motus.order.model.Product;
import br.com.motus.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  private final OrderRepository orderRepository;
  private final ProductService productService;

  public OrderService(OrderRepository orderRepository, ProductService productService) {
    this.orderRepository = orderRepository;
    this.productService = productService;
  }

  public Page<Order> list(Pageable pageable) {
    return orderRepository.findAll(pageable);
  }

  public Optional<OrderResponseDTO> findById(String id) {
    return orderRepository.findById(id)
        .map(order -> {
          List<OrderProductResponseDTO> products = orderRepository.findOrderWithProducts(
                  order.getId())
              .stream()
              .map(orderProductRow -> OrderProductResponseDTO.builder()
                  .id(orderProductRow.getProductId())
                  .code(orderProductRow.getCode())
                  .name(orderProductRow.getName())
                  .price(orderProductRow.getPrice())
                  .quantity(orderProductRow.getQuantity())
                  .build())
              .toList();

          return OrderResponseDTO.builder()
              .id(order.getId())
              .status(order.getStatus())
              .products(products)
              .dtCreated(order.getDtCreated())
              .dtUpdated(order.getDtUpdated())
              .build();
        });
  }

  @Transactional
  public OrderResponseDTO save(OrderRequestDTO orderRequestDTO) {

    Order order = Order.builder()
        .status("Criado")
        .dtCreated(LocalDateTime.now())
        .build();

    Order savedOrder = orderRepository.save(order);

    orderRequestDTO.products()
        .forEach(productOrderRequestDTO -> {
          Optional<Product> productOptional = productService.findById(
              productOrderRequestDTO.getProductId());
          productOptional.ifPresentOrElse(product -> {
                orderRepository.saveProductIntoOrder(
                    savedOrder.getId(),
                    productOrderRequestDTO.getProductId(),
                    productOrderRequestDTO.getQuantity()
                );
              },
              () -> {
                throw new CantCreateOrderProductDoesntExistException(
                    "Product with id: " + productOrderRequestDTO.getProductId() + " not exist.");
              });
        });

    List<OrderProductResponseDTO> productResponseDTOS = orderRepository.findOrderWithProducts(
            savedOrder.getId())
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

  @Transactional
  public Optional<OrderResponseDTO> replace(String orderId, OrderRequestDTO orderRequestDTO) {
    Optional<Order> orderOptional = orderRepository.findById(orderId);

    if (orderOptional.isPresent()) {
      Order order = orderOptional.get();
      order.setStatus(orderRequestDTO.status());
      order.setDtUpdated(LocalDateTime.now());

      orderRepository.save(order);
      orderRepository.deleteProductsFromOrderById(orderId);

      orderRequestDTO.products()
          .forEach(productOrderRequestDTO -> productService.findById(
                  productOrderRequestDTO.getProductId())
              .ifPresent(product -> orderRepository.saveProductIntoOrder(
                  order.getId(),
                  productOrderRequestDTO.getProductId(),
                  productOrderRequestDTO.getQuantity())));

      List<OrderProductResponseDTO> productResponseDTOS = orderRepository.findOrderWithProducts(
              order.getId())
          .stream()
          .map(orderProductRow ->
              OrderProductResponseDTO.builder()
                  .id(orderProductRow.getProductId())
                  .name(orderProductRow.getName())
                  .code(orderProductRow.getCode())
                  .price(orderProductRow.getPrice())
                  .build()
          ).toList();

      OrderResponseDTO orderResponseDTO = OrderResponseDTO.builder()
          .id(order.getId())
          .status(order.getStatus())
          .dtCreated(order.getDtCreated())
          .dtUpdated(order.getDtUpdated())
          .products(productResponseDTOS)
          .build();

      return Optional.of(orderResponseDTO);

    }

    return Optional.empty();

  }

  public OrderWithTotalPriceDTO getOrderWithTotalPrice(String id) {

    double sum = orderRepository.findOrderWithProducts(id)
        .stream()
        .map(orderProductRow -> orderProductRow.getPrice().multiply(
            BigDecimal.valueOf(orderProductRow.getQuantity()))
        ).mapToDouble(BigDecimal::doubleValue)
        .sum();

    return OrderWithTotalPriceDTO.builder()
        .orderId(id)
        .totalPrice(BigDecimal.valueOf(sum))
        .build();

  }
}
