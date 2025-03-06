package br.com.motus.order.controller;

import br.com.motus.order.controller.dto.order.OrderListResponseDTO;
import br.com.motus.order.controller.dto.order.OrderRequestDTO;
import br.com.motus.order.controller.dto.order.OrderResponseDTO;
import br.com.motus.order.controller.dto.order.OrderWithTotalPriceDTO;
import br.com.motus.order.exception.OrderNotFoundException;
import br.com.motus.order.service.OrderService;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/order")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping
  public ResponseEntity<Page<OrderListResponseDTO>> list(@PageableDefault Pageable pageable) {
    var orderList = orderService.list(pageable).map(OrderListResponseDTO::new);
    return ResponseEntity.ok().body(orderList);
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderResponseDTO> findById(@PathVariable("id") String id) {
    return orderService.findById(id)
        .map(orderResponseDTO -> ResponseEntity.ok().body(orderResponseDTO))
        .orElseThrow(() -> new OrderNotFoundException("Order with id: " + id + " was not found."));
  }

  @GetMapping("/{id}/fullprice")
  public ResponseEntity<OrderWithTotalPriceDTO> orderWithTotalPrice(@PathVariable("id") String id) {
    return ResponseEntity.ok().body(orderService.getOrderWithTotalPrice(id));
  }

  @PostMapping
  public ResponseEntity<OrderResponseDTO> create(
      @RequestBody @Valid OrderRequestDTO orderRequestDTO, UriComponentsBuilder uriBuilder) {
    var orderResponseDTO = orderService.save(orderRequestDTO);
    var uri = uriBuilder.path("/order/{id}").buildAndExpand(orderResponseDTO.getId()).toUri();
    return ResponseEntity.created(uri).body(orderResponseDTO);
  }

  // CRIAR ENDPOINT PARA ADICIONAR PRODUTO AO PEDIDO

  @PutMapping("/{id}")
  public ResponseEntity<OrderResponseDTO> replace(@PathVariable String id,
      @RequestBody @Valid OrderRequestDTO orderRequestDTO) {

    return orderService.replace(id, orderRequestDTO)
        .map(orderResponseDTO -> ResponseEntity.ok().body(orderResponseDTO))
        .orElseGet(() -> ResponseEntity.notFound().build());

  }


}