package com.fiapi.populator.dto;

import com.fiapi.converter.DtoConverter;
import com.fiapi.dto.AddressDto;
import com.fiapi.dto.OrderDto;
import com.fiapi.dto.OrderEntryDto;
import com.fiapi.dto.PaymentMethodDto;
import com.fiapi.model.AddressModel;
import com.fiapi.model.OrderEntryModel;
import com.fiapi.model.OrderModel;
import com.fiapi.model.PaymentMethodModel;
import com.fiapi.populator.base.DtoPopulator;

public class OrderDtoPopulator implements DtoPopulator<OrderModel, OrderDto> {

    private final DtoConverter<AddressModel, AddressDto> addressDtoConverter;
    private final DtoConverter<PaymentMethodModel, PaymentMethodDto> paymentMethodDtoConverter;
    private final DtoConverter<OrderEntryModel, OrderEntryDto> orderEntryDtoConverter;

    public OrderDtoPopulator(DtoConverter<AddressModel, AddressDto> addressDtoConverter,
                             DtoConverter<PaymentMethodModel, PaymentMethodDto> paymentMethodDtoConverter, DtoConverter<OrderEntryModel, OrderEntryDto> orderEntryDtoConverter) {
        this.addressDtoConverter = addressDtoConverter;
        this.paymentMethodDtoConverter = paymentMethodDtoConverter;
        this.orderEntryDtoConverter = orderEntryDtoConverter;
    }

    @Override
    public void populate(OrderModel source, OrderDto target) {
        target.setCode(source.getCode());
        target.setOwner(source.getOwner().getUsername());
        target.setBasePrice(source.getBasePrice());
        target.setDiscountPrice(source.getDiscountPrice());
        target.setTaxPrice(source.getTaxPrice());
        target.setTotalPrice(source.getTotalPrice());
        target.setCreationTime(source.getCreationTime().toString());

        target.setDeliveryAddress(addressDtoConverter.convert(source.getDeliveryAddress()));
        target.setPaymentMethod(paymentMethodDtoConverter.convert(source.getPaymentMethod()));
        target.setEntries(orderEntryDtoConverter.convertAll(source.getEntries()));
    }

}
