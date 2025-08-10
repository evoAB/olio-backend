package com.olio.Services.Transformers;

import com.olio.Dto.Request.ProductRequest;
import com.olio.Dto.Response.ProductResponse;
import com.olio.Model.Model.Product;

public class ProductTransformer {
    public static Product convertProductRequestToEntity (ProductRequest productRequest){
        return Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .imageUrl(productRequest.getImageUrl())
                .build();
    }

    public static ProductResponse convertEntityToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .categoryName(product.getCategory().getName())
                .build();
    }
}
