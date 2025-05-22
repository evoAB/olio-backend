package com.olio.Services.Transformers;

import com.olio.Dto.Request.CategoryRequest;
import com.olio.Dto.Response.CategoryResponse;
import com.olio.Model.Model.Category;

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
