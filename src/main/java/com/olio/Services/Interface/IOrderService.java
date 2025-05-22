package com.olio.Services.Interface;

import com.olio.Model.Model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(String email);
    List<Order> getOrders(String email);
}
