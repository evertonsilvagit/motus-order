package br.com.motus.order.controller;

import br.com.motus.order.controller.dto.product.ProductRequestDTO;
import br.com.motus.order.controller.dto.product.ProductResponseDTO;
import br.com.motus.order.exception.ProductNotFoundException;
import br.com.motus.order.model.Product;
import br.com.motus.order.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable("id") String id){

        Optional<Product> productOptional = productService.findById(id);

            if(productOptional.isPresent()) {
                ProductResponseDTO productResponseDTO = productOptional.map(product ->
                        ProductResponseDTO.builder()
                                .id(product.getId())
                                .code(product.getCode())
                                .name(product.getName())
                                .price(product.getPrice())
                                .dtCreated(product.getDtCreated())
                                .dtUpdated(product.getDtUpdated())
                                .build()
                ).get();

                return ResponseEntity.ok().body(productResponseDTO);
            } else {
                return ResponseEntity.notFound().build();
            }

    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody ProductRequestDTO productRequestDTO){

        Product product = Product.builder()
                .code(productRequestDTO.getCode())
                .name(productRequestDTO.getName())
                .price(productRequestDTO.getPrice())
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

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> replace(@PathVariable("id") String id, @RequestBody ProductRequestDTO productRequestDTO) {

        Product product = Product.builder()
                .code(productRequestDTO.getCode())
                .name(productRequestDTO.getName())
                .price(productRequestDTO.getPrice())
                .build();

        try {
            Product replacedProduct = productService.replace(id, product);

            ProductResponseDTO productResponseDTO = ProductResponseDTO.builder().build();
            productResponseDTO.setId(replacedProduct.getId());
            productResponseDTO.setCode(replacedProduct.getCode());
            productResponseDTO.setName(replacedProduct.getName());
            productResponseDTO.setPrice(replacedProduct.getPrice());
            productResponseDTO.setDtCreated(replacedProduct.getDtCreated());
            productResponseDTO.setDtUpdated(replacedProduct.getDtUpdated());

            return ResponseEntity.ok().body(productResponseDTO);
        } catch(ProductNotFoundException productNotFoundException) {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> delete(@PathVariable("id") String id){
        if(productService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
