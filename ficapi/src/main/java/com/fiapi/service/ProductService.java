package com.fiapi.service;

import com.fiapi.model.ProductModel;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<ProductModel> findProductByCode(String code);

    List<ProductModel> findAllProducts();

    List<ProductModel> findProductsByName(String name);

    List<ProductModel> findProductsByCategory(String category);

    List<ProductModel> findProductsByBrand(String brand);

    List<ProductModel> findProductsByStatus(String status);

    List<ProductModel> findProductsByCountryId(String countryId);

    void addProduct(ProductModel product);

    void updateProduct(ProductModel product);

    void deleteProduct(String productCode);

    Optional<ProductModel> findProductByCodeAndStatus(String code, String status);

}
