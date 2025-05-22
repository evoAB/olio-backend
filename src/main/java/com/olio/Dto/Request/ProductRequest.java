package com.olio.Dto.Request;

import com.olio.Model.Model.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequest {
    @NotBlank
    private String name;

    private String description;

    @NotNull
    @Min(0)
    private Double price;

    private String imageUrl;

    @NotNull
    private Long categoryId;

    public Double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
