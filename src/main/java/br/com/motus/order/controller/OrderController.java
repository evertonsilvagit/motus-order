package br.com.motus.order.controller;

import br.com.motus.order.controller.dto.order.OrderRequestDTO;
import br.com.motus.order.controller.dto.order.OrderResponseDTO;
import br.com.motus.order.controller.dto.order.OrderWithTotalPriceDTO;
import br.com.motus.order.model.Order;
import br.com.motus.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
                                .status(order.getStatus())
                                .dtCreated(order.getDtCreated())
                                .dtUpdated(order.getDtUpdated())
                                .build()
                )
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(orderList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> findById(@PathVariable("id") String id) {
        Optional<Order> orderOptional = orderService.findById(id);

        if (orderOptional.isPresent()) {
            OrderResponseDTO orderResponseDTO = orderOptional.map(order ->
                    OrderResponseDTO.builder()
                            .id(order.getId())
                            .status(order.getStatus())
                            .dtCreated(order.getDtCreated())
                            .dtUpdated(order.getDtUpdated())
                            .build()
            ).get();

            return ResponseEntity.ok().body(orderResponseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/fullprice")
    public ResponseEntity<OrderWithTotalPriceDTO> orderWithTotalPrice(@PathVariable("id") String id){
        return ResponseEntity.ok().body(orderService.getOrderWithTotalPrice(id));
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@RequestBody OrderRequestDTO orderRequestDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                orderService.save(orderRequestDTO)
        );
    }
}