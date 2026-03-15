package com.olio.services.impl;

import com.olio.model.*;
import com.olio.repository.CartRepository;
import com.olio.repository.OrderRepository;
import com.olio.repository.ProductRepository;
import com.olio.repository.UserRepository;
import com.olio.services.interfaces.OrderService;
import com.olio.services.interfaces.UserService;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private OrderService orderService;

    @Test
    void placeOrder_success() {
        // Arrange
        String email = "test@mail.com";

        User user = new User();
        user.setId(1L);

        Product product = new Product();
        product.setId(10L);
        product.setName("Laptop");
        product.setPrice(1000.0);
        product.setQuantity(5); // DB stock

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(2);

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setItems(List.of(cartItem));
        cart.setTotal(2000.0);

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        Mockito.when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
        Mockito.when(productRepository.findById(product.getId()))
                .thenReturn(Optional.of(product));
        Mockito.when(orderRepository.save(Mockito.any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Order order = orderService.placeOrder(email);

        // Assert
        assertNotNull(order);
        assertEquals(1, order.getItems().size());
        assertEquals(2000.0, order.getTotal());

        Mockito.verify(orderRepository, Mockito.times(1))
                .save(Mockito.any(Order.class));
        Mockito.verify(cartRepository, Mockito.times(1))
                .save(cart);
    }
}