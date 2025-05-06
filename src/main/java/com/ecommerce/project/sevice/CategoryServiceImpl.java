package com.ecommerce.project.sevice;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);

    }

    @Override
    public String deleteCategory(Long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        Category savedCategory =  categoryOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource  not found"));
        categoryRepository.delete(savedCategory);
        return "Category with CategoryId " + categoryId + " deleted successfully !!";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        Category savedCategory = categoryOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource  not found"));
        category.setCategoryId(categoryId);
        savedCategory = categoryRepository.save(category);
        return savedCategory;

    }
}
