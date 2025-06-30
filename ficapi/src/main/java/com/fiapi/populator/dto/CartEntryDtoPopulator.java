package com.fiapi.populator.dto;

import com.fiapi.converter.DtoConverter;
import com.fiapi.dto.CartEntryDto;
import com.fiapi.dto.ProductDto;
import com.fiapi.model.CartEntryModel;
import com.fiapi.model.ProductModel;
import com.fiapi.populator.base.DtoPopulator;

public class CartEntryDtoPopulator implements DtoPopulator<CartEntryModel, CartEntryDto> {

    private final DtoConverter<ProductModel, ProductDto> productDtoConverter;

    public CartEntryDtoPopulator(DtoConverter<ProductModel, ProductDto> productDtoConverter) {
        this.productDtoConverter = productDtoConverter;
    }

    @Override
    public void populate(CartEntryModel source, CartEntryDto target) {
        target.setQuantity(source.getQuantity());
        target.setBasePrice(source.getBasePrice());
        target.setDiscountPrice(source.getDiscountPrice());
        target.setTaxPrice(source.getTaxPrice());
        target.setTotalPrice(source.getTotalPrice());
        target.setProduct(productDtoConverter.convert(source.getProduct()));
    }

}
