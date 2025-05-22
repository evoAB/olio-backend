package com.olio.Dto.Request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryRequest {
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
}
