package com.ecommerce.project.repositories;

import com.ecommerce.project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryCategoryName(String categoryName);

    List<Product> findByBrandName(String brandName);

    List<Product> findByCategoryCategoryNameAndBrandName(String categoryName, String brandName);

    List<Product> findByproductName(String productName);

    List<Product> findByBrandNameAndProductName(String brandName, String productName);

    Long countByBrandNameAndProductName(String brandName, String productName);
}
