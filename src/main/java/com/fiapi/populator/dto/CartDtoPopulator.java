package com.fiapi.populator.dto;

import com.fiapi.converter.DtoConverter;
import com.fiapi.dto.AddressDto;
import com.fiapi.dto.CartDto;
import com.fiapi.dto.CartEntryDto;
import com.fiapi.dto.PaymentMethodDto;
import com.fiapi.model.AddressModel;
import com.fiapi.model.CartEntryModel;
import com.fiapi.model.CartModel;
import com.fiapi.model.PaymentMethodModel;
import com.fiapi.populator.base.DtoPopulator;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Objects;

public class CartDtoPopulator implements DtoPopulator<CartModel, CartDto> {

    private final DtoConverter<CartEntryModel, CartEntryDto> cartEntryDtoConverter;
    private final DtoConverter<PaymentMethodModel, PaymentMethodDto> paymentMethodDtoConverter;
    private final DtoConverter<AddressModel, AddressDto> addressDtoConverter;

    public CartDtoPopulator(DtoConverter<CartEntryModel, CartEntryDto> cartEntryDtoConverter,
                            DtoConverter<PaymentMethodModel, PaymentMethodDto> paymentMethodDtoConverter,
                            DtoConverter<AddressModel, AddressDto> addressDtoConverter) {
        this.cartEntryDtoConverter = cartEntryDtoConverter;
        this.paymentMethodDtoConverter = paymentMethodDtoConverter;
        this.addressDtoConverter = addressDtoConverter;
    }


    @Override
    public void populate(CartModel source, CartDto target) {
        target.setCode(source.getCode());
        target.setOwner(source.getOwner().getEmail());
        target.setBasePrice(source.getBasePrice());
        target.setDiscountPrice(source.getDiscountPrice());
        target.setTaxPrice(source.getTaxPrice());
        target.setTotalPrice(source.getTotalPrice());

        target.setCreationTime(source.getCreationTime().toString());
        target.setModifiedTime(source.getModifiedTime().toString());

        if (Objects.nonNull(source.getPaymentMethod())) {
            final PaymentMethodDto paymentMethodDto = paymentMethodDtoConverter.convert(source.getPaymentMethod());
            target.setPaymentMethod(paymentMethodDto);
        }

        if (CollectionUtils.isNotEmpty(source.getEntries())) {
            List<CartEntryDto> entries = cartEntryDtoConverter.convertAll(source.getEntries());
            target.setEntries(entries);
        }

        if (Objects.nonNull(source.getDeliveryAddress())) {
            AddressDto addressDto = addressDtoConverter.convert(source.getDeliveryAddress());
            target.setDeliveryAddress(addressDto);
        }

    }
}
