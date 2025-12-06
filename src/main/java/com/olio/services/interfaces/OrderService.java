package com.olio.services.interfaces;

import com.olio.model.Order;

import java.util.List;

public interface OrderService {
    Order placeOrder(String email);
    List<Order> getOrders(String email);
}
