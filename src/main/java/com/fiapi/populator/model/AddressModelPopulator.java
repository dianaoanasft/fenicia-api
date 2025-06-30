package com.fiapi.populator.model;

import com.fiapi.dto.AddressDto;
import com.fiapi.model.AddressModel;
import com.fiapi.populator.base.ModelPopulator;
import com.fiapi.service.I18NService;

public class AddressModelPopulator implements ModelPopulator<AddressDto, AddressModel> {

    private final I18NService i18NService;

    public AddressModelPopulator(I18NService i18NService) {
        this.i18NService = i18NService;
    }

    @Override
    public void populate(AddressDto source, AddressModel target) {
        target.setAlias(source.getAlias());
        target.setStreet(source.getStreet());
        target.setNumber(source.getNumber());
        target.setPostalCode(source.getPostalCode());
        target.setPhoneNumber(source.getPhoneNumber());
        target.setTown(source.getTown());

        target.setAdditionalInformation(source.getAdditionalInformation());

        target.setDeliveryAddress(source.isDeliveryAddress());
        target.setBillingAddress(source.isBillingAddress());
        target.setDefaultAddress(source.isDefaultAddress());

        populateRegionIfPresent(source, target);
        populateCountryIfPresent(source, target);
    }

    private void populateRegionIfPresent(AddressDto source, AddressModel target) {
        i18NService.fetchRegionByCode(source.getRegion())
                .ifPresent(target::setRegion);
    }

    private void populateCountryIfPresent(AddressDto source, AddressModel target) {
        i18NService.fetchCountryByCode(source.getCountry())
                .ifPresent(target::setCountry);
    }



}
