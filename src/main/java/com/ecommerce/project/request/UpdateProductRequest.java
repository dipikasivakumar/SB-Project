package com.ecommerce.project.request;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequest {

    private String productName;
    private BigDecimal price;
    private int inventory;
    private String brandName;
    private String description;
    private String categoryName;
}
