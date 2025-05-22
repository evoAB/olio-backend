package com.olio.Services.Interface;

import com.olio.Dto.Request.ProductRequest;
import com.olio.Dto.Response.ProductResponse;
import com.olio.Model.Model.Product;

import java.util.List;

public interface IProductService {
    ProductResponse createProduct(ProductRequest request);
    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(Long id);
    ProductResponse updateProduct(Long id, ProductRequest request);
    void deleteProduct(Long id);
}
