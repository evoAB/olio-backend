package com.olio.Dto.Response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private String categoryName;
    private String sellerName;
    public void setDescription(String description) {
        this.description = description;
    }
}

