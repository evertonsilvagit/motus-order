package br.com.motus.order.service;

import br.com.motus.order.exception.ProductNotFoundException;
import br.com.motus.order.model.OrderProduct;
import br.com.motus.order.model.Product;
import br.com.motus.order.repository.OrderProductRepository;
import br.com.motus.order.repository.OrderRepository;
import br.com.motus.order.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final OrderProductRepository orderProductRepository;


    public ProductService(ProductRepository productRepository, OrderProductRepository orderProductRepository){
        this.productRepository = productRepository;
        this.orderProductRepository = orderProductRepository;
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

    public boolean delete(String productId) {
        Optional<OrderProduct> optionalOrderProduct = orderProductRepository.findByProductId(productId);

        if(optionalOrderProduct.isEmpty()){
            productRepository.deleteById(productId);
            return true;
        }

        return false;
    }
}
