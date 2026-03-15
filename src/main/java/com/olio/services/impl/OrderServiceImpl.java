package com.olio.services.impl;

import com.olio.model.*;
import com.olio.repository.CartRepository;
import com.olio.repository.OrderRepository;
import com.olio.repository.ProductRepository;
import com.olio.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements com.olio.services.interfaces.OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Order placeOrder(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if(cart.getItems().isEmpty())
            throw new RuntimeException("Cart is Empty");

        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setTotal(cart.getTotal());

        List<OrderItem> items = cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            Integer quantity = productRepository.findById(product.getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"))
                    .getQuantity();

            if(product.getQuantity() < quantity){
                throw new RuntimeException("Not enough stock for quantity : " + product.getName());
            }

            return OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(cartItem.getQuantity())
                .price(product.getPrice())
                .build();
        }).toList();

        order.setItems(items);
        order.setTotal(items.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum()
        );

        cart.getItems().clear();
        cart.setTotal(0.0);
        cartRepository.save(cart);

        return orderRepository.save(order);
    }

    public List<Order> getOrders(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return orderRepository.findByUser(user);
    }
}
