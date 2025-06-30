package com.fiapi.populator.dto;

import com.fiapi.converter.DtoConverter;
import com.fiapi.dto.OrderEntryDto;
import com.fiapi.dto.ProductDto;
import com.fiapi.model.OrderEntryModel;
import com.fiapi.model.ProductModel;
import com.fiapi.populator.base.DtoPopulator;

public class OrderEntryDtoPopulator implements DtoPopulator<OrderEntryModel, OrderEntryDto> {

    private final DtoConverter<ProductModel, ProductDto> productDtoConverter;

    public OrderEntryDtoPopulator(DtoConverter<ProductModel, ProductDto> productDtoConverter) {
        this.productDtoConverter = productDtoConverter;
    }

    @Override
    public void populate(OrderEntryModel source, OrderEntryDto target) {
        target.setQuantity(source.getQuantity());
        target.setBasePrice(source.getBasePrice());
        target.setDiscountPrice(source.getDiscountPrice());
        target.setTaxPrice(source.getTaxPrice());
        target.setTotalPrice(source.getTotalPrice());
        target.setProduct(productDtoConverter.convert(source.getProduct()));
    }
}
