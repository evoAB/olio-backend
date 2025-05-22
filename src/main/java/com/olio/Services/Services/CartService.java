package com.olio.Services.Services;

import com.olio.Dto.Response.CartResponse;
import com.olio.Model.Model.Cart;
import com.olio.Model.Model.CartItem;
import com.olio.Model.Model.Product;
import com.olio.Model.Model.User;
import com.olio.Repository.Repository.CartItemRepository;
import com.olio.Repository.Repository.CartRepository;
import com.olio.Repository.Repository.ProductRepository;
import com.olio.Repository.Repository.UserRepository;
import com.olio.Services.Interface.ICartService;
import com.olio.Services.Transformers.CartTransformer;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public Cart getCartByEmail(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return cartRepository.findByUser(user)
                .orElseGet(() -> createCartForUser(user));
    }

    private Cart createCartForUser(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public CartResponse addItemToCart(String email, Long productId, int quantity) {
        Cart cart = getCartByEmail(email);
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new RuntimeException("Product not found"));

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if(existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cart.getItems().add(newItem);
        }

        updateCartTotal(cart);
        return CartTransformer.toDTO(cartRepository.save(cart));
    }

    @Override
    @Transactional
    public CartResponse updateItemToCart(String email, Long productId, int quantity) {
        Cart cart = getCartByEmail(email);
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new RuntimeException("Product not found"));

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if(existingItem.isPresent()) {
            CartItem item = existingItem.get();
            if(quantity <= 0){
                cart.getItems().remove(item);
            } else {
                item.setQuantity(quantity);
            }
        } else {
            throw new RuntimeException("Product not found in cart");
        }

        updateCartTotal(cart);
        return CartTransformer.toDTO(cartRepository.save(cart));
    }

    @Override
    @Transactional
    public Cart removeItemFromCart(String email, Long itemId) {
        Cart cart = getCartByEmail(email);

        CartItem itemToRemove = cart.getItems().stream()
                        .filter(item -> item.getProduct().getId().equals(itemId))
                                .findFirst()
                                        .orElseThrow(() -> new RuntimeException("Item not found in cart"));

        cart.getItems().remove(itemToRemove);
        cartItemRepository.delete(itemToRemove);

        updateCartTotal(cart);

        return cartRepository.save(cart);
    }

    @Override
    public void clearCart(String email) {
        Cart cart = getCartByEmail(email);
        cart.getItems().clear();
        cart.setTotal(0.0);
        cartRepository.save(cart);
    }

    private void updateCartTotal(Cart cart) {
        double total = cart.getItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
        cart.setTotal(total);
    }
}
