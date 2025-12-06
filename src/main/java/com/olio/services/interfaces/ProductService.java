package com.olio.services.interfaces;

import com.olio.dto.Request.ProductRequest;
import com.olio.dto.Response.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request, String sellerEmail);
    List<ProductResponse> getAllProducts();
    List<ProductResponse> getProductBySearch(String search, String sortBy, String sortOrder, Double minPrice, Double maxPrice);
    ProductResponse getProductById(Long id);
    List<ProductResponse> getProductsBySeller(String email);
    ProductResponse updateProduct(Long id, ProductRequest request);
    void deleteProduct(Long id);
}
