package com.olio.services.impl;

import com.olio.model.Cart;
import com.olio.model.Order;
import com.olio.model.OrderItem;
import com.olio.model.User;
import com.olio.repository.CartRepository;
import com.olio.repository.OrderRepository;
import com.olio.repository.UserRepository;
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

    @Override
    public Order placeOrder(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        Cart cart = cartRepository.findByUser(user).orElseThrow();

        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setTotal(cart.getTotal());

        List<OrderItem> items = cart.getItems().stream().map(cartItem -> OrderItem.builder()
                .order(order)
                .product(cartItem.getProduct())
                .quantity(cartItem.getQuantity())
                .price(cartItem.getProduct().getPrice())
                .build()).toList();

        order.setItems(items);

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
