package br.com.motus.order.controller;

import br.com.motus.order.controller.dto.product.request.ProductCreateRequestDTO;
import br.com.motus.order.controller.dto.product.response.ProductResponseDTO;
import br.com.motus.order.model.Product;
import br.com.motus.order.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> list(){

        List<ProductResponseDTO> productList = productService.list()
                .stream()
                .map(product ->
                    ProductResponseDTO.builder()
                            .id(product.getId())
                            .code(product.getCode())
                            .name(product.getName())
                            .price(product.getPrice())
                            .dtCreated(product.getDtCreated())
                            .dtUpdated(product.getDtUpdated())
                            .build()
                )
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(productList);

    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody ProductCreateRequestDTO productCreateRequestDTO){

        Product product = Product.builder()
                .code(productCreateRequestDTO.getCode())
                .name(productCreateRequestDTO.getName())
                .price(productCreateRequestDTO.getPrice())
                .build();

        Product createdProduct = productService.save(product);
        ProductResponseDTO productResponseDTO = ProductResponseDTO.builder()
                .id(createdProduct.getId())
                .code(createdProduct.getCode())
                .name(createdProduct.getName())
                .price(createdProduct.getPrice())
                .dtCreated(createdProduct.getDtCreated())
                .dtUpdated(createdProduct.getDtUpdated())
                .build();

        return ResponseEntity.ok().body(productResponseDTO);
    }

}
