package com.olio.Controller.Controller;

import com.olio.Dto.Request.ProductRequest;
import com.olio.Dto.Response.ProductResponse;
import com.olio.Model.Model.Product;
import com.olio.Services.Interface.IProductService;
import com.olio.Services.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ProductResponse> create(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.createProduct(request, userDetails.getUsername()));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "name") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortOrder,
            @RequestParam(required = false, defaultValue = "0.0") Double minPrice,
            @RequestParam(required = false, defaultValue = "1000000") Double maxPrice
    ) {
        if(search!=null && !search.isEmpty())
            return ResponseEntity.ok(productService.getProductBySearch(search, sortBy, sortOrder, minPrice, maxPrice));
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/seller")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<List<ProductResponse>> getSellerProducts(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(productService.getProductsBySeller(userDetails.getUsername()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
