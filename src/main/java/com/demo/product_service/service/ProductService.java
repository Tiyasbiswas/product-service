package com.demo.product_service.service;

import com.demo.product_service.entity.Product;
import com.demo.product_service.exception.ProductNotFoundException;
import com.demo.product_service.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public Product createProduct(Product product) {
        if (product.getPrice() == null || product.getPrice().signum() <= 0) {
            throw new IllegalArgumentException("Product price must be greater than zero");
        }
        if (product.getQuantity() == null || product.getQuantity() < 0) {
            throw new IllegalArgumentException("Product quantity cannot be negative");
        }
        return productRepository.save(product);
    }
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found: " + id));
    }
    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = getProductById(id);

        if (updatedProduct.getPrice() == null ||
                updatedProduct.getPrice().signum() <= 0) {
            throw new IllegalArgumentException(
                    "Product price must be greater than zero");
        }

        if (updatedProduct.getQuantity() == null ||
                updatedProduct.getQuantity() < 0) {
            throw new IllegalArgumentException(
                    "Product quantity cannot be negative");
        }

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setQuantity(updatedProduct.getQuantity());

        return productRepository.save(existingProduct);
    }
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }
}
