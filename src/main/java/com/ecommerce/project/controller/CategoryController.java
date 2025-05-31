package com.ecommerce.project.controller;

import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.response.APIResponse;
import com.ecommerce.project.sevice.category.ICategoryService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/allCategories")
    public ResponseEntity<APIResponse> getAllCategories() {
        try {
            List<Category> allCategories = categoryService.getAllCategories();
            return  ResponseEntity.ok(new APIResponse("Found!!" , allCategories));
        } catch (Exception e) {
            return  ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse("Error",INTERNAL_SERVER_ERROR ));
        }
    }


    @PostMapping("/addCategory")
    public ResponseEntity<APIResponse> addCategory(@RequestBody Category category) {
        try {
            Category addedcategory = categoryService.addCategory(category);
            return ResponseEntity.ok(new APIResponse("Category added successfully !!", addedcategory));
        } catch (Exception e) {
            return  ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse("Error",INTERNAL_SERVER_ERROR ));
        }
    }

    @GetMapping("/getCategory/{categoryId}")
    public ResponseEntity<APIResponse> getCategoryById(@PathVariable Long categoryId) {
        try {
            Category category = categoryService.getCategoryById(categoryId);
            return  ResponseEntity.ok(new APIResponse("Success!!" , category));
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null ));
        }
    }

    @GetMapping("/getCategory/{categoryName}")
    public ResponseEntity<APIResponse> getCategoryByName(@PathVariable String categoryName) {
        try {
            Category category = categoryService.getCategoryByName(categoryName);
            return  ResponseEntity.ok(new APIResponse("Success!!" , category));
        } catch (Exception e) {
            return  ResponseEntity.status(NOT_FOUND).body(new APIResponse("Category Not Found!!" ,NOT_FOUND));
        }
    }

    @DeleteMapping("/category/delete/{categoryId}")
    public ResponseEntity<APIResponse> deleteCategory(@PathVariable Long categoryId) {
        try {
            categoryService.deleteCategory(categoryId);
            return  ResponseEntity.ok(new APIResponse("Category Deleted!!", categoryId));
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PutMapping(value ="/category/update/{categoryId}")
    public ResponseEntity<APIResponse> updateCategory(@RequestBody Category category, @PathVariable Long categoryId) {
        try {
            Category savedCategory = categoryService.updateCategory(category, categoryId);
            return ResponseEntity.ok(new APIResponse("Category Updated!!", categoryId));
        } catch (ResponseStatusException e) {
            return  ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }



}
