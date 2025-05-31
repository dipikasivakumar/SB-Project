package com.ecommerce.project.sevice.product;


import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Image;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.repositories.CategoryRepository;
import com.ecommerce.project.repositories.ImageRepository;
import com.ecommerce.project.repositories.ProductRepository;
import com.ecommerce.project.request.AddProductRequest;
import com.ecommerce.project.request.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService{

    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final CategoryRepository categoryRepository;



    @Override
    public Product addProduct(AddProductRequest request) {
       String categoryName =  request.getCategory().getCategoryName();
        Category category = Optional.ofNullable( categoryRepository.findByCategoryName(categoryName))
                .orElseGet(()-> {
                    Category newCategory= new Category(categoryName);
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));

    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(), request.getPrice(), request.getInventory(), request.getBrand() ,
                request.getDescription(), category);
    }


    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product Not Found!!"));
    }

    @Override
    public void deleteProductById(Long productId) {
        productRepository.findById(productId).ifPresentOrElse(productRepository :: delete,
                () -> {throw new ResourceNotFoundException("Product Not Found!!");});
    }

    @Override
    public Product updateProduct(UpdateProductRequest request, Long Id) {
        return productRepository.findById(Id).map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository :: save)
                .orElseThrow(()-> new ResourceNotFoundException("Product Not Found!!"));
    }

    private Product updateExistingProduct(Product existingProduct, UpdateProductRequest request) {
        existingProduct.setProductName(request.getProductName());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setBrandName(request.getBrandName());
        existingProduct.setDescription(request.getDescription());
        Category category = categoryRepository.findByCategoryName(request.getCategoryName());
        if (category == null) {
            throw new ResourceNotFoundException("Category not found: " + category);
        }
        existingProduct.setCategory(category);
        return existingProduct;
    }


    @Override
    public List<Product> getAllProductsByCategory(String categoryName) {
        return productRepository.findByCategoryCategoryName(categoryName);
    }

    @Override
    public List<Product> getAllProductsByBrand(String brand) {
        return productRepository.findByBrandName(brand);
    }

    @Override
    public List<Product> getAllProductsByCategoryAndBrand(String categoryName, String brandName) {
        return productRepository.findByCategoryCategoryNameAndBrandName(categoryName,brandName);
    }

    @Override
    public List<Product> getProductByName(String productName) {
        return productRepository.findByproductName(productName);
    }

    @Override
    public List<Product> getAllProductsByBrandAndName(String brandName, String name) {
        return productRepository.findByBrandNameAndProductName(brandName, name);
    }

    @Override
    public Long countByBrandNameAndProductName(String brandName, String productName) {
        return productRepository.countByBrandNameAndProductName(brandName, productName);
    }

//    @Override
//    public ProductDTO convertToDTO(Product product) {
//        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
//        List<Image> images = imageRepository.findByProduct_ProductId(product.getProductId());
//        List<ImageDTO> imageDtos = images.stream().map(image -> modelMapper.map(image, ImageDTO.class)).toList();
//        productDTO.setImages(imageDtos);
//        return productDTO;
//    }
//
//    @Override
//    public List<ProductDTO> getConvertedProducts(List<Product> products) {
//        return products.stream().map(this::convertToDTO).toList();
//    }

}
