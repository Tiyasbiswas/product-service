
package com.demo.product_service.integration;

import com.demo.product_service.entity.Product;
import com.demo.product_service.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void saveAndFindProductSuccessfully() {

        Product product = new Product();
        product.setName("Integration Laptop");
        product.setDescription("Test Description");
        product.setPrice(new BigDecimal("1500.00"));
        product.setQuantity(10);

        Product savedProduct = productRepository.save(product);

        assertNotNull(savedProduct.getId());

        Optional<Product> foundProduct =
                productRepository.findById(savedProduct.getId());

        assertTrue(foundProduct.isPresent());
        assertEquals("Integration Laptop", foundProduct.get().getName());
        assertEquals(new BigDecimal("1500.00"), foundProduct.get().getPrice());
    }
}
