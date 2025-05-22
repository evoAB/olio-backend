package com.olio.Services.Transformers;

import com.olio.Dto.Response.CartItemResponse;
import com.olio.Dto.Response.CartResponse;
import com.olio.Dto.Response.ProductResponse;
import com.olio.Model.Model.Cart;
import com.olio.Model.Model.Product;

import java.util.List;

public class CartTransformer {
    public static CartResponse toDTO(Cart cart) {
        CartResponse dto = new CartResponse();
        dto.setId(cart.getId());
        dto.setTotal(cart.getTotal());

        List<CartItemResponse> items = cart.getItems().stream().map(item -> {
            CartItemResponse itemResponse = new CartItemResponse();
            itemResponse.setId(item.getId());
            itemResponse.setQuantity(item.getQuantity());

            Product product = item.getProduct();
            ProductResponse productResponse = new ProductResponse();
            productResponse.setId(product.getId());
            productResponse.setName(product.getName());
            productResponse.setPrice(product.getPrice());

            itemResponse.setProduct(productResponse);
            return itemResponse;
        }).toList();

        dto.setItems(items);
        return dto;
    }
}
