package com.olio.Services.Services;

import com.olio.Dto.Request.CategoryRequest;
import com.olio.Dto.Response.CategoryResponse;
import com.olio.Exception.ResourceNotFoundException;
import com.olio.Model.Model.Category;
import com.olio.Model.Model.Product;
import com.olio.Repository.Repository.CategoryRepository;
import com.olio.Repository.Repository.ProductRepository;
import com.olio.Services.Interface.ICategoryService;
import com.olio.Services.Transformers.CategoryTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
//    @Autowired
//    private CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = CategoryTransformer.convertCategoryRequestToEntity(request);
        return CategoryTransformer.convertEntityCategoryResponse(categoryRepository.save(category));
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream()
                .map(CategoryTransformer ::convertEntityCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return CategoryTransformer.convertEntityCategoryResponse(category);
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(request.getName());
        return CategoryTransformer.convertEntityCategoryResponse(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }


    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));
        return productRepository.findByCategory(category);
    }
}