package com.olio.Controller.Controller;

import com.olio.Dto.Response.CartResponse;
import com.olio.Model.Model.Cart;
import com.olio.Services.Interface.ICartService;
import com.olio.Services.Services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final ICartService cartService;

    @GetMapping
    public ResponseEntity<Cart> getCart(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(cartService.getCartByEmail(userDetails.getUsername()));
    }

    @PostMapping("/add")
    public ResponseEntity<CartResponse> addItem(@AuthenticationPrincipal UserDetails userDetails,
                                                @RequestParam Long productId,
                                                @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.addItemToCart(userDetails.getUsername(), productId, quantity));
    }
    @PutMapping("/update")
    public ResponseEntity<CartResponse> updateCart(@AuthenticationPrincipal UserDetails userDetails,
                                                @RequestParam Long productId,
                                                @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.updateItemToCart(userDetails.getUsername(), productId, quantity));
    }

    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<Cart> removeItem(@AuthenticationPrincipal UserDetails userDetails,
                                           @PathVariable Long itemId) {
        return ResponseEntity.ok(cartService.removeItemFromCart(userDetails.getUsername(), itemId));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal UserDetails userDetails){
        cartService.clearCart(userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
