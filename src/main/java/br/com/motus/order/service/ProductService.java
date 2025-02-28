package br.com.motus.order.service;

import br.com.motus.order.exception.ProductNotFoundException;
import br.com.motus.order.model.Product;
import br.com.motus.order.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public Optional<Product> findById(String id) {
        return productRepository.findById(id);
    }

    public List<Product> findAllByIds(List<String> productIds){
        return productRepository.findAllById(productIds);
    }

    public Product replace(String id, Product product) throws ProductNotFoundException {

        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isPresent()){
            Product savedProduct = productOptional.get();
            savedProduct.setCode(product.getCode());
            savedProduct.setName(product.getName());
            savedProduct.setPrice(product.getPrice());
            savedProduct.setDtUpdated(LocalDateTime.now());
            return productRepository.save(savedProduct);
        }

        throw new ProductNotFoundException();

    }

    public void delete(String id) {
        productRepository.deleteById(id);
    }
}
