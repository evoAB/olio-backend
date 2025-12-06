package com.olio.services.interfaces;

import com.olio.dto.Response.CartResponse;
import com.olio.model.Cart;

public interface CartService {
    Cart getCartByEmail(String email);
    CartResponse addItemToCart(String email, Long productId, int quantity);
    CartResponse updateItemToCart(String email, Long productId, int quantity);
    Cart removeItemFromCart(String email, Long itemId);
    void clearCart(String email);
}
