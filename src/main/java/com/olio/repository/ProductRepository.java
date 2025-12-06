package com.olio.repository;

import com.olio.model.Category;
import com.olio.model.Product;
import com.olio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryName(String categoryName);
    List<Product> findByCategory(Category category);
    List<Product> findByNameContainingIgnoreCase(String prefix);
    List<Product> findBySeller(User seller);
    List<Product> findBySellerId(Long sellerId);

}
