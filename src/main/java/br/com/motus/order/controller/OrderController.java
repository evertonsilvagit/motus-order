package br.com.motus.order.controller;

import br.com.motus.order.controller.dto.order.OrderRequestDTO;
import br.com.motus.order.controller.dto.order.OrderResponseDTO;
import br.com.motus.order.controller.dto.product.ProductResponseDTO;
import br.com.motus.order.model.Order;
import br.com.motus.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> list() {
        List<OrderResponseDTO> orderList = orderService.list()
                .stream()
                .map(order ->
                        OrderResponseDTO.builder()
                                .id(order.getId())
                                .products(order.getProducts().stream()
                                        .map(product -> ProductResponseDTO.builder()
                                                .id(product.getId())
                                                .code(product.getCode())
                                                .name(product.getName())
                                                .price(product.getPrice())
                                                .build())
                                        .collect(Collectors.toList()))
                                .status(order.getStatus())
                                .dtCreated(order.getDtCreated())
                                .dtUpdated(order.getDtUpdated())
                                .build()
                )
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(orderList);
    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<OrderResponseDTO> findById(@PathVariable("id") String id) {
//        Optional<Order> orderOptional = orderService.findById(id);
//
//        if (orderOptional.isPresent()) {
//            OrderResponseDTO orderResponseDTO = orderOptional.map(order ->
//                    OrderResponseDTO.builder()
//                            .id(order.getId())
//                            .products(order.getProducts().stream()
//                                    .map(product -> ProductResponseDTO.builder()
//                                            .id(product.getId())
//                                            .code(product.getCode())
//                                            .name(product.getName())
//                                            .price(product.getPrice())
//                                            .build())
//                                    .collect(Collectors.toList()))
//                            .status(order.getStatus())
//                            .dtCreated(order.getDtCreated())
//                            .dtUpdated(order.getDtUpdated())
//                            .build()
//            ).get();
//
//            return ResponseEntity.ok().body(orderResponseDTO);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@RequestBody OrderRequestDTO orderRequestDTO) {
        Order order = Order.builder()
                .status(orderRequestDTO.getStatus())
                .build();

        Order createdOrder = orderService.save(order, orderRequestDTO.getProductIds());

        OrderResponseDTO orderResponseDTO = OrderResponseDTO.builder()
                .id(createdOrder.getId())
                .products(createdOrder.getProducts().stream()
                        .map(product -> ProductResponseDTO.builder()
                                .id(product.getId())
                                .code(product.getCode())
                                .name(product.getName())
                                .price(product.getPrice())
                                .build())
                        .collect(Collectors.toList()))
                .status(createdOrder.getStatus())
                .dtCreated(createdOrder.getDtCreated())
                .dtUpdated(createdOrder.getDtUpdated())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponseDTO);
    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<OrderResponseDTO> replace(@PathVariable("id") String id, @RequestBody OrderRequestDTO orderRequestDTO) {
//        Order order = Order.builder()
//                .products(orderRequestDTO.getProducts().stream()
//                        .map(productRequest -> Product.builder()
//                                .id(productRequest.getId())
//                                .code(productRequest.getCode())
//                                .name(productRequest.getName())
//                                .price(productRequest.getPrice())
//                                .build())
//                        .collect(Collectors.toList()))
//                .status(orderRequestDTO.getStatus())
//                .build();
//
//        try {
//            Order replacedOrder = orderService.replace(id, order);
//
//            OrderResponseDTO orderResponseDTO = OrderResponseDTO.builder()
//                    .id(replacedOrder.getId())
//                    .products(replacedOrder.getProducts().stream()
//                            .map(product -> ProductResponseDTO.builder()
//                                    .id(product.getId())
//                                    .code(product.getCode())
//                                    .name(product.getName())
//                                    .price(product.getPrice())
//                                    .build())
//                            .collect(Collectors.toList()))
//                    .status(replacedOrder.getStatus())
//                    .dtCreated(replacedOrder.getDtCreated())
//                    .dtUpdated(replacedOrder.getDtUpdated())
//                    .build();
//
//            return ResponseEntity.ok().body(orderResponseDTO);
//        } catch (OrderNotFoundException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<OrderResponseDTO> delete(@PathVariable("id") String id) {
//        orderService.delete(id);
//        return ResponseEntity.noContent().build();
//    }
//
}