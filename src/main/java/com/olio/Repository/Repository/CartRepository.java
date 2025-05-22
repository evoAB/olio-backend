package com.olio.Repository.Repository;

import com.olio.Model.Model.Cart;
import com.olio.Model.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
