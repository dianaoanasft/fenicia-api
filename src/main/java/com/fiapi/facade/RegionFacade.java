package com.fiapi.facade;

import com.fiapi.dto.RegionDto;
import com.fiapi.exception.ConversionException;
import com.fiapi.exception.ModelSaveException;

import java.util.List;
import java.util.Optional;

public interface RegionFacade {

    void saveRegion(RegionDto regionDto) throws ConversionException, ModelSaveException;

    Optional<RegionDto> getRegionByCode(String code) throws ConversionException;

    void deleteRegion(String code) throws ModelSaveException;

    void updateRegion(RegionDto regionDto) throws ConversionException, ModelSaveException;

    Optional<RegionDto> getRegionByName(String name) throws ConversionException;

    List<RegionDto> getRegionsByCountry(String country) throws ConversionException;

    Optional<RegionDto> getRegionByIsoCode(String isoCode) throws ConversionException;

}
