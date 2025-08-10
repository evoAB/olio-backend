package com.olio.Services.Interface;

import com.olio.Dto.Request.ProductRequest;
import com.olio.Dto.Response.ProductResponse;
import com.olio.Model.Model.Product;

import java.util.List;

public interface IProductService {
    ProductResponse createProduct(ProductRequest request, String sellerEmail);
    List<ProductResponse> getAllProducts();
    List<ProductResponse> getProductBySearch(String search, String sortBy, String sortOrder, Double minPrice, Double maxPrice);
    ProductResponse getProductById(Long id);
    List<ProductResponse> getProductsBySeller(String email);
    ProductResponse updateProduct(Long id, ProductRequest request);
    void deleteProduct(Long id);
}
