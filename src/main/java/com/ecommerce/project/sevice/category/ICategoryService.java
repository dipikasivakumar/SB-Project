package com.ecommerce.project.sevice.category;

import com.ecommerce.project.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ICategoryService {

    List<Category> getAllCategories();
    Category getCategoryById(Long Id);
    Category getCategoryByName(String name);
    Category addCategory(Category category);

    void deleteCategory(Long categoryId);

    Category updateCategory(Category category, Long categoryId);
}
