package com.onilicious.EcommerceStartUp.service;

import com.onilicious.EcommerceStartUp.dto.request.ProductCreateRequestDTO;
import com.onilicious.EcommerceStartUp.dto.request.ProductUpdateRequestDTO;
import com.onilicious.EcommerceStartUp.entity.Product;
import com.onilicious.EcommerceStartUp.mapper.ProductMapper;
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
    public Product createProduct(ProductCreateRequestDTO request) {
        Product product = ProductMapper.toEntity(request);
        return productRepo.save(product);
    }

    //Update product
    public Product updateProduct(Long id, ProductUpdateRequestDTO request) {
        Product existingProduct = getProductById(id);
        ProductMapper.updateEntity(existingProduct, request);

        return productRepo.save(existingProduct);
    }

    //Delete product
    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }

}
