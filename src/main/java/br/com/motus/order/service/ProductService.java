package br.com.motus.order.service;

import br.com.motus.order.model.Product;
import br.com.motus.order.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> list(){
        return productRepository.findAll();
    }

    public Product save(Product product) {
        product.setDtCreated(LocalDateTime.now());
        return productRepository.save(product);
    }
}
