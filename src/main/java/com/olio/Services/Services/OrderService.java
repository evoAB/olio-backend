package com.olio.Services.Services;

import com.olio.Model.Model.Cart;
import com.olio.Model.Model.Order;
import com.olio.Model.Model.OrderItem;
import com.olio.Model.Model.User;
import com.olio.Repository.Repository.CartRepository;
import com.olio.Repository.Repository.OrderRepository;
import com.olio.Repository.Repository.UserRepository;
import com.olio.Services.Interface.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
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
