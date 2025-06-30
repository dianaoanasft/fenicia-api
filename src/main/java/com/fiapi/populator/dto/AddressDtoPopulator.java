package com.fiapi.populator.dto;

import com.fiapi.converter.DtoConverter;
import com.fiapi.dto.AddressDto;
import com.fiapi.dto.CountryDto;
import com.fiapi.dto.RegionDto;
import com.fiapi.model.AddressModel;
import com.fiapi.model.CountryModel;
import com.fiapi.model.RegionModel;
import com.fiapi.populator.base.DtoPopulator;

import java.util.Objects;

public class AddressDtoPopulator implements DtoPopulator<AddressModel, AddressDto> {

    private final DtoConverter<RegionModel, RegionDto> regionDtoConverter;
    private final DtoConverter<CountryModel, CountryDto> countryDtoConverter;

    public AddressDtoPopulator(DtoConverter<RegionModel, RegionDto> regionDtoConverter, DtoConverter<CountryModel, CountryDto> countryDtoConverter) {
        this.regionDtoConverter = regionDtoConverter;
        this.countryDtoConverter = countryDtoConverter;
    }

    @Override
    public void populate(AddressModel source, AddressDto target) {
        target.setAlias(source.getAlias());
        target.setStreet(source.getStreet());
        target.setNumber(source.getNumber());
        target.setPostalCode(source.getPostalCode());
        target.setPhoneNumber(source.getPhoneNumber());
        target.setTown(source.getTown());
        target.setAdditionalInformation(source.getAdditionalInformation());

        if (Objects.nonNull(source.getRegion())) {
            RegionDto regionDto = regionDtoConverter.convert(source.getRegion());
            target.setRegion(regionDto.getIsoCode());
        }

        if (Objects.nonNull(source.getCountry())) {
            CountryDto countryDto = countryDtoConverter.convert(source.getCountry());
            target.setCountry(countryDto.getIsoCode());
        }

        target.setDefaultAddress(source.isDefaultAddress());
        target.setBillingAddress(source.isBillingAddress());
        target.setDeliveryAddress(source.isDeliveryAddress());
    }

}
