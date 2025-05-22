package com.olio.Services.Interface;

import com.olio.Dto.Request.CategoryRequest;
import com.olio.Dto.Response.CategoryResponse;
import com.olio.Model.Model.Product;

import java.util.List;

public interface ICategoryService {
    CategoryResponse createCategory(CategoryRequest request);
    List<CategoryResponse> getAllCategories();
    CategoryResponse getCategoryById(Long id);
    CategoryResponse updateCategory(Long id, CategoryRequest request);
    void deleteCategory(Long id);
    List<Product> getProductsByCategory(Long categoryId);
}
