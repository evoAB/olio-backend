package com.olio.services.interfaces;

import com.olio.dto.Request.CategoryRequest;
import com.olio.dto.Response.CategoryResponse;
import com.olio.model.Product;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);
    List<CategoryResponse> getAllCategories();
    CategoryResponse getCategoryById(Long id);
    CategoryResponse updateCategory(Long id, CategoryRequest request);
    void deleteCategory(Long id);
    List<Product> getProductsByCategory(Long categoryId);
}
