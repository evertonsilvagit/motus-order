package br.com.motus.order.controller;

import br.com.motus.order.controller.dto.product.ProductRequestDTO;
import br.com.motus.order.controller.dto.product.ProductResponseDTO;
import br.com.motus.order.exception.ProductNotFoundException;
import br.com.motus.order.model.Product;
import br.com.motus.order.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public ResponseEntity<Page<ProductResponseDTO>> list(@PageableDefault Pageable pageable) {
    var productList = productService.list(pageable).map(ProductResponseDTO::new);
    return ResponseEntity.ok().body(productList);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductResponseDTO> findById(@PathVariable("id") String id) {
    return productService.findById(id)
        .map(product -> ResponseEntity.ok().body(new ProductResponseDTO(product)))
        .orElseThrow(() -> new ProductNotFoundException("Product with id: " + id + " was not found."));
  }

  @PostMapping
  public ResponseEntity<ProductResponseDTO> create(
      @RequestBody @Valid ProductRequestDTO productRequestDTO) {

    var product = Product.builder()
          .code(productRequestDTO.code())
          .name(productRequestDTO.name())
          .price(productRequestDTO.price())
        .build();

    var createdProduct = productService.save(product);
    var productResponseDTO = new ProductResponseDTO(createdProduct);

    return ResponseEntity.ok().body(productResponseDTO);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductResponseDTO> replace(@PathVariable("id") String id,
      @RequestBody @Valid ProductRequestDTO productRequestDTO) {

    var product = Product.builder()
          .code(productRequestDTO.code())
          .name(productRequestDTO.name())
          .price(productRequestDTO.price())
        .build();

    var replacedProduct = productService.replace(id, product);
    var productResponseDTO = new ProductResponseDTO(replacedProduct);

    return ResponseEntity.ok().body(productResponseDTO);

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") String id) {
    productService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
