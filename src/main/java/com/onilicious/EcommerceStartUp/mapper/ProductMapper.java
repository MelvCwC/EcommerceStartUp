package com.onilicious.EcommerceStartUp.mapper;

import com.onilicious.EcommerceStartUp.dto.request.ProductCreateRequestDTO;
import com.onilicious.EcommerceStartUp.dto.request.ProductUpdateRequestDTO;
import com.onilicious.EcommerceStartUp.dto.response.ProductResponseDTO;
import com.onilicious.EcommerceStartUp.entity.Product;

public class ProductMapper {

    public static Product toEntity(ProductCreateRequestDTO request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategory(request.getCategory());
        return product;
    }

    public static void updateEntity(Product product, ProductUpdateRequestDTO request) {
        if(request.getName() != null) { product.setName(request.getName()); }
        if(request.getCategory() != null) { product.setCategory(request.getCategory()); }
        if(request.getDescription() != null) { product.setDescription(request.getDescription()); }
        if(request.getStockQuantity() != null) { product.setStockQuantity(request.getStockQuantity()); }
        if(request.getPrice() != null) { product.setPrice(request.getPrice()); }
    }

    public static ProductResponseDTO toResponse(Product product) {
        ProductResponseDTO response = new ProductResponseDTO();
        response.setId(product.getId());
        response.setCategory(product.getCategory());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setStockQuantity(product.getStockQuantity());
        response.setCreatedAt(product.getCreatedAt());
        response.setPrice(product.getPrice());
        return response;

    }
}
