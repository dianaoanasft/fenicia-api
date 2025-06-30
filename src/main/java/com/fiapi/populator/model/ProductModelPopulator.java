package com.fiapi.populator.model;

import com.fiapi.dto.ProductDto;
import com.fiapi.enums.StatusEnum;
import com.fiapi.exception.ConversionException;
import com.fiapi.model.ProductModel;
import com.fiapi.populator.base.ModelPopulator;
import com.fiapi.service.I18NService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;

import java.util.Objects;

public class ProductModelPopulator implements ModelPopulator<ProductDto, ProductModel> {

    private final I18NService i18NService;

    public ProductModelPopulator(I18NService i18NService) {
        this.i18NService = i18NService;
    }

    @Override
    public void populate(@NonNull ProductDto source, @NonNull ProductModel target) {
        validateSource(source);
        target.setCode(source.getSku());
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        if (StringUtils.isNotEmpty(source.getStatus())) {
            target.setStatus(StatusEnum.valueOf(source.getStatus()));
        }
        target.setWeight(source.getWeight());
        target.setBasePrice(source.getBasePrice());
        target.setDiscountPrice(source.getDiscountPrice());
        target.setTaxPrice(source.getTaxPrice());
        target.setImageUrl(source.getImageUrl());
        target.setPackingQuantity(source.getPackageQuantity());
        target.setBrand(source.getBrand());
        target.setCategory(source.getCategory());
        i18NService.fetchCountryByCode(source.getCountry())
                        .ifPresent(target::setCountry);
    }

    private void validateSource(@NonNull ProductDto source) {
        if (StringUtils.isEmpty(source.getSku())
        || StringUtils.isEmpty(source.getName())
        || StringUtils.isEmpty(source.getStatus())
        || StringUtils.isEmpty(source.getCountry())
        || Objects.isNull(source.getBasePrice())) {
            throw  new ConversionException("INVALID PRODUCT DTO");
        }
    }

}
