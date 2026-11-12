package com.demo.product_service.service;

import com.demo.product_service.entity.Product;
import com.demo.product_service.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void createProductSuccessfully() {

        Product product = new Product();
        product.setName("Laptop");
        product.setDescription("Dell Laptop");
        product.setPrice(new BigDecimal("999.99"));
        product.setQuantity(10);

        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.createProduct(product);
        assertNotNull(savedProduct);
        assertEquals("Laptop", savedProduct.getName());
        assertEquals(new BigDecimal("999.99"), savedProduct.getPrice());
        verify(productRepository, times(1)).save(product);
    }
    @Test
    void createProductWithInvalidPrice() {

        Product product = new Product();
        product.setName("Laptop");
        product.setPrice(new BigDecimal("0"));
        product.setQuantity(10);
        assertThrows(IllegalArgumentException.class,
                () -> productService.createProduct(product));
        verify(productRepository, never()).save(any(Product.class));
    }
    @Test
    void createProductWithInvalidQuantity() {

        Product product = new Product();
        product.setName("Laptop");
        product.setPrice(new BigDecimal("999.99"));
        product.setQuantity(-5);

        assertThrows(IllegalArgumentException.class,
                () -> productService.createProduct(product));

        verify(productRepository, never()).save(any(Product.class));
    }
    @Test
    void getAllProductsSuccessfully() {

        Product product1 = new Product();
        product1.setName("Laptop");

        Product product2 = new Product();
        product2.setName("Mobile");

        when(productRepository.findAll())
                .thenReturn(java.util.List.of(product1, product2));

        var products = productService.getAllProducts();

        assertEquals(2, products.size());
        assertEquals("Laptop", products.get(0).getName());
        assertEquals("Mobile", products.get(1).getName());

        verify(productRepository, times(1)).findAll();
    }
    @Test
    void getProductByIdSuccessfully() {

        Product product = new Product();
        product.setName("Laptop");
        product.setPrice(new BigDecimal("999.99"));
        product.setQuantity(10);

        when(productRepository.findById(1L))
                .thenReturn(java.util.Optional.of(product));

        Product result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals("Laptop", result.getName());

        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void getProductByIdNotFound() {

        when(productRepository.findById(99L))
                .thenReturn(java.util.Optional.empty());

        assertThrows(
                com.demo.product_service.exception.ProductNotFoundException.class,
                () -> productService.getProductById(99L)
        );

        verify(productRepository, times(1)).findById(99L);
    }@Test
    void updateProductSuccessfully() {

        Product existingProduct = new Product();
        existingProduct.setName("Old Laptop");
        existingProduct.setDescription("Old Description");
        existingProduct.setPrice(new BigDecimal("800.00"));
        existingProduct.setQuantity(5);

        Product updatedProduct = new Product();
        updatedProduct.setName("New Laptop");
        updatedProduct.setDescription("Updated Description");
        updatedProduct.setPrice(new BigDecimal("1200.00"));
        updatedProduct.setQuantity(10);

        when(productRepository.findById(1L))
                .thenReturn(java.util.Optional.of(existingProduct));

        when(productRepository.save(existingProduct))
                .thenReturn(existingProduct);

        Product result = productService.updateProduct(1L, updatedProduct);

        assertEquals("New Laptop", result.getName());
        assertEquals("Updated Description", result.getDescription());
        assertEquals(new BigDecimal("1200.00"), result.getPrice());
        assertEquals(10, result.getQuantity());

        verify(productRepository, times(1)).save(existingProduct);
    }@Test
    void deleteProductSuccessfully() {

        Product product = new Product();
        product.setName("Laptop");

        when(productRepository.findById(1L))
                .thenReturn(java.util.Optional.of(product));

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).delete(product);
    }
}