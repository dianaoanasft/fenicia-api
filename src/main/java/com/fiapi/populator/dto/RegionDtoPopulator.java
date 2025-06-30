package com.fiapi.populator.dto;

import com.fiapi.dto.RegionDto;
import com.fiapi.model.RegionModel;
import com.fiapi.populator.base.DtoPopulator;

import java.util.Objects;

public class RegionDtoPopulator implements DtoPopulator<RegionModel, RegionDto> {

    @Override
    public void populate(RegionModel source, RegionDto target) {
        target.setCode(source.getCode());
        target.setName(source.getName());
        target.setIsoCode(source.getIsoCode());

        if (Objects.nonNull(source.getCountry())) {
            target.setCountry(source.getCountry().getCode());
        }
    }
}
