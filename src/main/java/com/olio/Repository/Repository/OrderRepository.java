package com.olio.Repository.Repository;

import com.olio.Model.Model.Order;
import com.olio.Model.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
