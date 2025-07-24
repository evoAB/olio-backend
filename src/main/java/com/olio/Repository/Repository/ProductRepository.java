package com.olio.Repository.Repository;

import com.olio.Model.Model.Category;
import com.olio.Model.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryName(String categoryName);
    List<Product> findByCategory(Category category);
    List<Product> findByNameContainingIgnoreCase(String prefix);
}
