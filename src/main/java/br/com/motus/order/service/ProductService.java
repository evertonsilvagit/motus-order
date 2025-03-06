package br.com.motus.order.service;

import br.com.motus.order.exception.CantDeleteProductException;
import br.com.motus.order.exception.ProductNotFoundException;
import br.com.motus.order.model.Product;
import br.com.motus.order.repository.OrderProductRepository;
import br.com.motus.order.repository.ProductRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  private final ProductRepository productRepository;

  private final OrderProductRepository orderProductRepository;


  public ProductService(ProductRepository productRepository,
      OrderProductRepository orderProductRepository) {
    this.productRepository = productRepository;
    this.orderProductRepository = orderProductRepository;
  }

  public Page<Product> list(Pageable pageable) {
    return productRepository.findAll(pageable);
  }

  public Product save(Product product) {
    product.setDtCreated(LocalDateTime.now());
    return productRepository.save(product);
  }

  public Optional<Product> findById(String id) {
    return productRepository.findById(id);
  }

  public boolean existsById(String id) {
    return productRepository.existsById(id);
  }

  public List<Product> findAllByIds(List<String> productIds) {
    return productRepository.findAllById(productIds);
  }

  public Product replace(String id, Product product) {
    var productOptional = productRepository.findById(id);

    if (productOptional.isPresent()) {
      var savedProduct = productOptional.get();
      savedProduct.setCode(product.getCode());
      savedProduct.setName(product.getName());
      savedProduct.setPrice(product.getPrice());
      savedProduct.setDtUpdated(LocalDateTime.now());

      return productRepository.save(savedProduct);
    }

    throw new ProductNotFoundException("Product with id: " + id + " was not found.");
  }

  public void delete(String productId) {
    var optionalOrderProduct = orderProductRepository.findByProductId(productId);

    if (optionalOrderProduct.isEmpty()) {
      productRepository.deleteById(productId);
      return;
    }

    throw new CantDeleteProductException(
        "Product with id: " + productId + " can't be deleted because is inside order: "
            + optionalOrderProduct.get().getOrderId());
  }
}
