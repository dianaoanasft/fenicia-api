package com.fiapi.facade.impl;

import com.fiapi.converter.DtoConverter;
import com.fiapi.converter.ModelConverter;
import com.fiapi.dto.RegionDto;
import com.fiapi.exception.ConversionException;
import com.fiapi.exception.ModelSaveException;
import com.fiapi.facade.RegionFacade;
import com.fiapi.model.RegionModel;
import com.fiapi.service.I18NService;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public class DefaultRegionFacade implements RegionFacade {

    private final I18NService i18NService;
    private final ModelConverter<RegionDto, RegionModel> regionModelConverter;
    private final DtoConverter<RegionModel, RegionDto> regionDtoConverter;

    public DefaultRegionFacade(I18NService i18NService, ModelConverter<RegionDto, RegionModel> regionModelConverter, DtoConverter<RegionModel, RegionDto> regionDtoConverter) {
        this.i18NService = i18NService;
        this.regionModelConverter = regionModelConverter;
        this.regionDtoConverter = regionDtoConverter;
    }

    @Override
    public void saveRegion(@NotNull RegionDto regionDto) throws ConversionException, ModelSaveException {
        final RegionModel region = regionModelConverter.convert(regionDto);
        i18NService.saveRegion(region);
    }

    @Override
    public Optional<RegionDto> getRegionByCode(String code) throws ConversionException {
        return i18NService.fetchRegionByCode(code)
                .map(regionDtoConverter::convert);
    }

    @Override
    public void deleteRegion(String code) throws ModelSaveException {
        i18NService.deleteRegion(code);
    }

    @Override
    public void updateRegion(RegionDto regionDto) throws ConversionException, ModelSaveException {
        final RegionModel region = i18NService.fetchRegionByCode(regionDto.getCode())
                .orElseThrow(() -> new ModelSaveException("Region not found"));

        region.setName(regionDto.getName());

        i18NService.saveRegion(region);
    }

    @Override
    public Optional<RegionDto> getRegionByName(String name) throws ConversionException {
        return i18NService.fetchRegionByName(name)
                .map(regionDtoConverter::convert);
    }

    @Override
    public List<RegionDto> getRegionsByCountry(String country) throws ConversionException {
        return i18NService.fetchRegionsByCountry(country)
                .stream()
                .map(regionDtoConverter::convert)
                .toList();
    }

    @Override
    public Optional<RegionDto> getRegionByIsoCode(String isoCode) throws ConversionException {
        return i18NService.fetchRegionByIsoCode(isoCode)
                .map(regionDtoConverter::convert);
    }

}
