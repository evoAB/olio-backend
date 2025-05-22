package com.olio.Services.Interface;

import com.olio.Dto.Response.CartResponse;
import com.olio.Model.Model.Cart;

public interface ICartService {
    Cart getCartByEmail(String email);
    CartResponse addItemToCart(String email, Long productId, int quantity);
    CartResponse updateItemToCart(String email, Long productId, int quantity);
    Cart removeItemFromCart(String email, Long itemId);
    void clearCart(String email);
}
