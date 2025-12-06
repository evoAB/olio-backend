package com.olio.services.transformers;

import com.olio.dto.Request.CategoryRequest;
import com.olio.dto.Response.CategoryResponse;
import com.olio.model.Category;

public class CategoryTransformer {
    public static Category convertCategoryRequestToEntity(CategoryRequest categoryRequest) {
        return Category.builder()
                .name(categoryRequest.getName())
                .build();
    }
    public static CategoryResponse convertEntityCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
