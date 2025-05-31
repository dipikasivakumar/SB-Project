package com.ecommerce.project.controller;

import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.request.AddProductRequest;
import com.ecommerce.project.request.UpdateProductRequest;
import com.ecommerce.project.response.APIResponse;
import com.ecommerce.project.sevice.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/allProducts")
    public ResponseEntity<APIResponse> getAllProducts() {
        try {
            List<Product> allProducts = productService.getAllProducts();
//            List<ProductDTO> convertedProducts = productService.getConvertedProducts(allProducts);
            return  ResponseEntity.ok(new APIResponse("Found!!" , allProducts));
        } catch (Exception e) {
            return  ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse("Error",INTERNAL_SERVER_ERROR ));
        }
    }

    @GetMapping("/getProductById")
    public ResponseEntity<APIResponse> getProductById(@RequestParam Long productId) {
        try {
            Product product = productService.getProductById(productId);
//            ProductDTO productDTO = productService.convertToDTO(product);
            return  ResponseEntity.ok(new APIResponse("Success!!" , product));
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(NOT_FOUND).body(new APIResponse(e.getMessage(),null ));
        }
    }

    @GetMapping("/getProductByName")
    public ResponseEntity<APIResponse> getProductByName(@RequestParam String name) {
            List<Product> products = productService.getProductByName(name);
            if(products.isEmpty()) {
                return  ResponseEntity.status(NOT_FOUND).body(new APIResponse("Product Not Found!!" ,products));
            }
                return  ResponseEntity.ok(new APIResponse("Success!!" , products));
    }

    @GetMapping("/getAllProductsByCategoryName")
    public ResponseEntity<APIResponse> getAllProductsByCategory(@RequestParam String categoryName) {
            List<Product> products = productService.getAllProductsByCategory(categoryName);
            if(products.isEmpty()) {
                return  ResponseEntity.status(NOT_FOUND).body(new APIResponse("Product for requested Category Not Found!!" ,categoryName));
            }
            return  ResponseEntity.ok(new APIResponse("Success!!" , products));
    }

    @GetMapping("/getAllProductsByBrandName")
    public ResponseEntity<APIResponse> getAllProductsByBrand(@RequestParam String brandName) {

            List<Product> products = productService.getAllProductsByBrand(brandName);
            if(products.isEmpty()) {
                return  ResponseEntity.status(NOT_FOUND).body(new APIResponse(null,brandName));
            }
            return  ResponseEntity.ok(new APIResponse("Success!!" , products));

    }

    @GetMapping("/getAllProductsByCategoryNameAndBrandName")
    public ResponseEntity<APIResponse> getAllProductsByCategoryAndBrand(@RequestParam String categoryName, @RequestParam String brandName) {

            List<Product> products = productService.getAllProductsByCategoryAndBrand(categoryName, brandName);
            if(products.isEmpty()) {
                return  ResponseEntity.status(NOT_FOUND).body(new APIResponse("Products for requested Category/ Brand Not Found!!" ,NOT_FOUND));
            }
            return  ResponseEntity.ok(new APIResponse("Success!!" , products));
    }

    @GetMapping("/getAllProductsByBrandNameAndProductname")
    public ResponseEntity<APIResponse> getAllProductsByBrandAndName(@RequestParam String brandName, @RequestParam String name) {
            List<Product> products = productService.getAllProductsByBrandAndName(brandName, name);
            if(products.isEmpty()) {
                return  ResponseEntity.status(NOT_FOUND).body(new APIResponse("Brand/ Product Name Not Found!!" ,NOT_FOUND));
            }
            return  ResponseEntity.ok(new APIResponse("Success!!" , products));
    }

    @PostMapping("/addProduct")
    public ResponseEntity<APIResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product addedProduct = productService.addProduct(product);
            return ResponseEntity.ok(new APIResponse("Product added successfully !!", addedProduct));
        } catch (Exception e) {
            return  ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse("Error",INTERNAL_SERVER_ERROR ));
        }
    }

    @DeleteMapping("/product/delete/{productId}")
    public ResponseEntity<APIResponse> deleteProductById(@PathVariable Long productId){
        try {
            productService.deleteProductById(productId);
            return  ResponseEntity.ok(new APIResponse("Product Deleted!!", productId));
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }

    }

    @PutMapping("/product/update/{Id}")
    public ResponseEntity<APIResponse> updateProduct(@RequestBody UpdateProductRequest request, @PathVariable Long Id) {
        try {
            Product updatedProduct=  productService.updateProduct(request, Id);
            return ResponseEntity.ok(new APIResponse("Product Updated!!", Id));
        } catch (ResponseStatusException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/count/getProducts/{brandName}/{productName}")
    public ResponseEntity<APIResponse> countByBrandNameAndProductName(@PathVariable String brandName, @PathVariable String productName) {
        Long count = productService.countByBrandNameAndProductName(brandName, productName);
        if(count == 0) {
            return  ResponseEntity.status(NOT_FOUND).body(new APIResponse("Brand/ Product Name Not Found!!" ,NOT_FOUND));
        }
        return  ResponseEntity.ok(new APIResponse("Success!!" , count));
    }

    
}
