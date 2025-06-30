package com.fiapi.populator.model;

import com.fiapi.dto.RegionDto;
import com.fiapi.model.RegionModel;
import com.fiapi.populator.base.ModelPopulator;

public class RegionModelPopulator implements ModelPopulator<RegionDto, RegionModel> {

    @Override
    public void populate(RegionDto source, RegionModel target) {
        target.setCode(source.getCode());
        target.setName(source.getName());
        target.setIsoCode(source.getIsoCode());
    }

}
