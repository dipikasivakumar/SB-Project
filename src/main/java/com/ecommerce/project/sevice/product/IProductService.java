package com.ecommerce.project.sevice.product;

import com.ecommerce.project.model.Product;
import com.ecommerce.project.request.AddProductRequest;
import com.ecommerce.project.request.UpdateProductRequest;

import  java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest request);
    List<Product> getAllProducts();
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(UpdateProductRequest product, Long Id);
    List<Product> getAllProductsByCategory(String categoryName);
    List<Product> getAllProductsByBrand(String brand);
    List<Product> getAllProductsByCategoryAndBrand(String categoryName, String brand);
    List<Product> getProductByName(String name);
    List<Product> getAllProductsByBrandAndName(String brandName, String name);
    Long countByBrandNameAndProductName(String brandName, String productName);
//    ProductDTO convertToDTO(Product product);

//    List<ProductDTO> getConvertedProducts(List<Product> products);
}
