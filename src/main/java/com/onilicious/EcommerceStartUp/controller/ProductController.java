package com.onilicious.EcommerceStartUp.controller;

import com.onilicious.EcommerceStartUp.dto.request.ProductCreateRequestDTO;
import com.onilicious.EcommerceStartUp.dto.request.ProductUpdateRequestDTO;
import com.onilicious.EcommerceStartUp.dto.response.ProductResponseDTO;
import com.onilicious.EcommerceStartUp.entity.Product;
import com.onilicious.EcommerceStartUp.mapper.ProductMapper;
import com.onilicious.EcommerceStartUp.service.ProductService;
import jakarta.validation.Valid;
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
    public List<ProductResponseDTO> getAllProducts() {
        return productService.getAllProduct().stream().map(ProductMapper::toResponse).toList();
    }

    /*
     * Get product by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(ProductMapper.toResponse(product));
    }

    /*
     * Get products by name
     */
    @GetMapping("/name/{name}")
    public List<ProductResponseDTO> getProductByName(@PathVariable String name) {
        return productService.getProductByName(name).stream().map(ProductMapper::toResponse).toList();
    }

    /*
     * Get products by category
     */
    @GetMapping("/category/{category}")
    public List<ProductResponseDTO> getProductByCategory(@PathVariable String category) {
        return productService.getProductByCategory(category).stream().map(ProductMapper::toResponse).toList();
    }

    /*
     * Add product
     */
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductCreateRequestDTO request) {
        Product product = productService.createProduct(request);
        return ResponseEntity.ok(ProductMapper.toResponse(product));
    }

    /*
     * Update product
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductUpdateRequestDTO request) {
        Product product = productService.updateProduct(id, request);
        return ResponseEntity.ok(ProductMapper.toResponse(product));
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
