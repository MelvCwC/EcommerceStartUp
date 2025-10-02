package com.onilicious.EcommerceStartUp.service;

import com.onilicious.EcommerceStartUp.entity.Product;
import com.onilicious.EcommerceStartUp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepo;

    //Constructor inject dependencies
    public ProductService(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    //Get all product
    public List<Product> getAllProduct() {
        return productRepo.findAll();
    }

    //Find all product from the same category
    public List<Product> getProductByCategory(String category) {
        return productRepo.findByCategoryIgnoreCase(category);
    }

    //Get product by name
    public List<Product> getProductByName(String name) {
        return productRepo.findByNameContainingIgnoreCase(name);
    }

    //Get product by Id
    public Product getProductById(Long id) {
        return productRepo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    //Add product
    public Product addProduct(Product product) {
        return productRepo.save(product);
    }

    //Update product
    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = productRepo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setStockQuantity(updatedProduct.getStockQuantity());

        return productRepo.save(existingProduct);
    }

    //Delete product
    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }

}
