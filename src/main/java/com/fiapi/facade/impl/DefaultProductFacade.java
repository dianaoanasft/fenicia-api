package com.fiapi.facade.impl;

import com.fiapi.converter.DtoConverter;
import com.fiapi.converter.ModelConverter;
import com.fiapi.dto.ProductDto;
import com.fiapi.facade.ProductFacade;
import com.fiapi.model.ProductModel;
import com.fiapi.populator.base.ModelPopulator;
import com.fiapi.service.ProductService;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public class DefaultProductFacade implements ProductFacade {

    private final ModelConverter<ProductDto, ProductModel> productModelConverter;
    private final DtoConverter<ProductModel, ProductDto> productDtoConverter;
    private final ModelPopulator<ProductDto, ProductModel> productModelPopulator;
    private final ProductService productService;

    public DefaultProductFacade(ModelConverter<ProductDto, ProductModel> productModelConverter,
                                DtoConverter<ProductModel, ProductDto> productDtoConverter,
                                ModelPopulator<ProductDto, ProductModel> productModelPopulator,
                                ProductService productService) {
        this.productModelConverter = productModelConverter;
        this.productDtoConverter = productDtoConverter;
        this.productModelPopulator = productModelPopulator;
        this.productService = productService;
    }

    @Override
    public void addProduct(@NonNull ProductDto product) {
        final ProductModel productModel = productModelConverter.convert(product);
        productService.addProduct(productModel);
    }

    @Override
    public void updateProduct(@NonNull ProductDto product) {
        productService.findProductByCode(product.getSku())
                .ifPresent(existingProduct -> {
                    productModelPopulator.populate(product, existingProduct);
                    productService.updateProduct(existingProduct);
                });
    }

    @Override
    public void deleteProduct(@NonNull String productCode) {
        productService.deleteProduct(productCode);
    }

    @Override
    public Optional<ProductDto> findProductByCode(@NonNull String code) {
        return productService.findProductByCode(code)
                .map(productDtoConverter::convert);
    }

    @Override
    public Optional<ProductDto> findProductByCodeAndStatus(@NonNull String code, @NonNull String status) {
        return productService.findProductByCodeAndStatus(code, status)
                .map(productDtoConverter::convert);
    }

    @Override
    public List<ProductDto> findAllProducts() {
        return productService.findAllProducts()
                .stream()
                .map(productDtoConverter::convert)
                .toList();
    }

    @Override
    public List<ProductDto> findProductsByName(@NonNull String name) {
        return productService.findProductsByName(name)
                .stream()
                .map(productDtoConverter::convert)
                .toList();
    }

    @Override
    public List<ProductDto> findProductsByCategory(@NonNull String category) {
            return productService.findProductsByCategory(category)
                    .stream()
                    .map(productDtoConverter::convert)
                    .toList();
    }

    @Override
    public List<ProductDto> findProductsByBrand(@NonNull String brand) {
        return productService.findProductsByBrand(brand)
                .stream()
                .map(productDtoConverter::convert)
                .toList();
    }

    @Override
    public List<ProductDto> findProductsByStatus(@NonNull String status) {
        return productService.findProductsByStatus(status)
                .stream()
                .map(productDtoConverter::convert)
                .toList();
    }

    @Override
    public List<ProductDto> findProductsByCountryId(@NonNull String countryId) {
        return productService.findProductsByCountryId(countryId)
                .stream()
                .map(productDtoConverter::convert)
                .toList();
    }
}
