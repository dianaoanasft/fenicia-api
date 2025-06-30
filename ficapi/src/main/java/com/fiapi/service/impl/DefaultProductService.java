package com.fiapi.service.impl;

import com.fiapi.model.ProductModel;
import com.fiapi.repository.ProductRepository;
import com.fiapi.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

public class DefaultProductService implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultProductService.class);

    private final ProductRepository productRepository;

    public DefaultProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<ProductModel> findProductByCode(String code) {
        final ProductModel product = productRepository.findByCode(code);
        return Optional.ofNullable(product);
    }

    @Override
    public List<ProductModel> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<ProductModel> findProductsByName(String name) {
        final Iterable<ProductModel> byNameContaining = productRepository.findByNameContaining(name);
        return StreamSupport.stream(byNameContaining.spliterator(), false)
                .toList();
    }

    @Override
    public List<ProductModel> findProductsByCategory(String category) {
        final Iterable<ProductModel> byCategory = productRepository.findByCategory(category);
        return StreamSupport.stream(byCategory.spliterator(), false)
                .toList();
    }

    @Override
    public List<ProductModel> findProductsByBrand(String brand) {
        final Iterable<ProductModel> byBrand = productRepository.findByBrand(brand);
        return StreamSupport.stream(byBrand.spliterator(), false)
                .toList();
    }

    @Override
    public List<ProductModel> findProductsByStatus(String status) {
        final Iterable<ProductModel> byStatus = productRepository.findByStatus(status);
        return StreamSupport.stream(byStatus.spliterator(), false)
                .toList();
    }

    @Override
    public List<ProductModel> findProductsByCountryId(String countryId) {
        final Iterable<ProductModel> byCountryId = productRepository.findByCountryId(countryId);
        return StreamSupport.stream(byCountryId.spliterator(), false)
                .toList();
    }

    @Override
    public void addProduct(ProductModel product) {
        if (Objects.nonNull(product.getPk())) {
            logger.warn("Product is new {}, generating new primary key", product.getCode());
        }
        final ProductModel savedProduct = productRepository.save(product);
        if (Objects.isNull(savedProduct.getPk())) {
            logger.error("Failed to save product: {}", product.getCode());
            throw new RuntimeException("Failed to save product");
        }
        logger.info("Product saved successfully: {}", savedProduct.getPk());
    }

    @Override
    public void updateProduct(ProductModel product) {
        final ProductModel savedProduct = productRepository.save(product);
        if (Objects.isNull(savedProduct.getPk())) {
            logger.error("Failed to update product");
            throw new RuntimeException("Failed to update product");
        }
    }

    @Override
    public void deleteProduct(String productCode) {
        Optional<ProductModel> productByCode = this.findProductByCode(productCode);
        if (productByCode.isPresent()) {
            productRepository.delete(productByCode.get());
            logger.info("Product deleted successfully");
        } else {
            logger.warn("Product not found for deletion");
        }
    }

    @Override
    public Optional<ProductModel> findProductByCodeAndStatus(String code, String status) {
        final ProductModel product = productRepository.findByCodeAndStatus(code, status);
        return Optional.ofNullable(product);
    }

}
