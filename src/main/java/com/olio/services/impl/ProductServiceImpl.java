package com.olio.services.impl;

import com.olio.dto.Request.ProductRequest;
import com.olio.dto.Response.ProductResponse;
import com.olio.exceptions.AccessDeniedException;
import com.olio.exceptions.ResourceNotFoundException;
import com.olio.model.Category;
import com.olio.model.Product;
import com.olio.model.User;
import com.olio.repository.CategoryRepository;
import com.olio.repository.ProductRepository;
import com.olio.repository.UserRepository;
import com.olio.services.transformers.ProductTransformer;
import com.olio.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements com.olio.services.interfaces.ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public ProductResponse createProduct(ProductRequest request, String sellerEmail) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        User seller = userRepository.findByEmail(sellerEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found"));

        if (seller.getRole() != Role.SELLER) {
            throw new AccessDeniedException("Only sellers can create products");
        }

        Product product = ProductTransformer.convertProductRequestToEntity(request);
        product.setCategory(category);
        product.setSeller(seller);

        return ProductTransformer.convertEntityToProductResponse(productRepository.save(product));
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductTransformer::convertEntityToProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getProductBySearch(String search, String sortBy, String sortOrder, Double minPrice, Double maxPrice){
        return productRepository.findByNameContainingIgnoreCase(search)
                .stream()
                .filter(p-> {
                    if(minPrice!=null && p.getPrice() < minPrice) return false;
                    if(maxPrice!=null && p.getPrice() > maxPrice) return false;
                    return true;
                })
                .sorted((p1, p2) -> {
                    int comparison = 0;

                    if ("price".equalsIgnoreCase(sortBy)) {
                        comparison = Double.compare(p1.getPrice(), p2.getPrice());
                    } else if ("name".equalsIgnoreCase(sortBy)) {
                        comparison = p1.getName().compareToIgnoreCase(p2.getName());
                    }

                    return "desc".equalsIgnoreCase(sortOrder) ? -comparison : comparison;
                })
                .map(ProductTransformer::convertEntityToProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return ProductTransformer.convertEntityToProductResponse(product);
    }

    @Override
    public List<ProductResponse> getProductsBySeller(String email) {
        User seller = userRepository.findByEmail(email)
                .filter(user -> user.getRole() == Role.SELLER)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found"));

        return productRepository.findBySellerId(seller.getId()).stream()
                .map(ProductTransformer::convertEntityToProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setImageUrl(request.getImageUrl());
        product.setQuantity(request.getQuantity());
        product.setCategory(category);

        return ProductTransformer.convertEntityToProductResponse(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
