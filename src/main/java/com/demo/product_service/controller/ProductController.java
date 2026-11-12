package com.demo.product_service.controller;

import com.demo.product_service.entity.Product;
import com.demo.product_service.service.ProductService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Create Product
    @PostMapping
//   public Product createProduct(@RequestBody Product product) {return productService.createProduct(product); }
    public Product createProduct(@Valid @RequestBody Product product){
        return productService.createProduct(product);
    }
    // Get All Products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Get Product By ID
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }
   // Product.java only define the rules. @Valid activates those rules when Spring receives a request.

    @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable Long id,
            @Valid@RequestBody Product product) {

        return productService.updateProduct(id, product);
    }
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "Product deleted successfully";
    }
}
