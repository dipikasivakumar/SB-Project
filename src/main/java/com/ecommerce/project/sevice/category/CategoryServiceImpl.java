package com.ecommerce.project.sevice.category;

import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService{

    @Autowired
    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long Id) {
        return categoryRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Category Not Found!!"));
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName);
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.ofNullable(getCategoryByName(category.getCategoryName())).filter(c -> !categoryRepository.existsByCategoryName(c.getCategoryName()))
                .map(categoryRepository :: save).orElseThrow(() -> new RuntimeException("Category already Present!!"));
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.findById(categoryId).ifPresentOrElse(categoryRepository :: delete, () -> {throw new ResourceNotFoundException("Category not found");});
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
         return Optional.ofNullable(getCategoryById(categoryId)).map(existingCategory-> {
            existingCategory.setCategoryName(category.getCategoryName());
            return categoryRepository.save(existingCategory);
        }).orElseThrow( ()-> new ResourceNotFoundException("Category not Found !!"));
    }
}
