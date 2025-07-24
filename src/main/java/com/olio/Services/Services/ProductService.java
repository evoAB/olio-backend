package com.olio.Services.Services;

import com.olio.Dto.Request.ProductRequest;
import com.olio.Dto.Response.ProductResponse;
import com.olio.Exception.ResourceNotFoundException;
import com.olio.Model.Model.Category;
import com.olio.Model.Model.Product;
import com.olio.Repository.Repository.CategoryRepository;
import com.olio.Repository.Repository.ProductRepository;
import com.olio.Services.Interface.IProductService;
//import com.olio.Services.Mapper.ProductMapper;
import com.olio.Services.Transformers.ProductTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.transform.Transformer;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Product product = ProductTransformer.convertProductRequestToEntity(request);
        product.setCategory(category);

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
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setImageUrl(request.getImageUrl());
        product.setCategory(category);

        return ProductTransformer.convertEntityToProductResponse(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
