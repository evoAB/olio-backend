package com.olio.services.transformers;

import com.olio.dto.Request.ProductRequest;
import com.olio.dto.Response.ProductResponse;
import com.olio.model.Product;

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
                .sellerName(
                        product.getSeller() != null ? product.getSeller().getName() : null
                )
                .build();
    }
}
