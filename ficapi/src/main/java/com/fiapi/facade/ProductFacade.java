package com.fiapi.facade;

import com.fiapi.dto.ProductDto;

import java.util.List;
import java.util.Optional;

public interface ProductFacade {

    void addProduct(ProductDto product);

    void updateProduct(ProductDto product);

    void deleteProduct(String productCode);

    Optional<ProductDto> findProductByCode(String code);

    Optional<ProductDto> findProductByCodeAndStatus(String code, String status);

    List<ProductDto> findAllProducts();

    List<ProductDto> findProductsByName(String name);

    List<ProductDto> findProductsByCategory(String category);

    List<ProductDto> findProductsByBrand(String brand);

    List<ProductDto> findProductsByStatus(String status);

    List<ProductDto> findProductsByCountryId(String countryId);

}
