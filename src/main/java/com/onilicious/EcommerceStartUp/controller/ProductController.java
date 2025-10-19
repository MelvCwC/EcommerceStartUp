package com.onilicious.EcommerceStartUp.controller;

import com.onilicious.EcommerceStartUp.entity.Product;
import com.onilicious.EcommerceStartUp.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /*
     * Get all product
     */
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProduct();
    }

    /*
     * Get product by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    /*
     * Get products by name
     */
    @GetMapping("/name/{name}")
    public List<Product> getProductByName(@PathVariable String name) {
        return productService.getProductByName(name);
    }

    /*
     * Get products by category
     */
    @GetMapping("/category/{category}")
    public List<Product> getProductByCategory(@PathVariable String category) {
        return productService.getProductByCategory(category);
    }

    /*
     * Add product
     */
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.addProduct(product));
    }

    /*
     * Update product
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updateProduct) {
        Product product = productService.updateProduct(id, updateProduct);
        return ResponseEntity.ok(product);
    }

    /*
     * Delete product
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }
}
