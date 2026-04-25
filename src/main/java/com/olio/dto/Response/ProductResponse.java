package com.olio.dto.Response;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private Integer quantity;
    private String categoryName;
    private String sellerName;
    public void setDescription(String description) {
        this.description = description;
    }
}

