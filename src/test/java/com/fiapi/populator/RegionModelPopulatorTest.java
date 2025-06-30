package com.fiapi.populator;

import com.fiapi.dto.RegionDto;
import com.fiapi.model.RegionModel;
import com.fiapi.populator.model.RegionModelPopulator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegionModelPopulatorTest {

    private RegionModelPopulator regionModelPopulator;

    @BeforeEach
    void setUp() {
        regionModelPopulator = new RegionModelPopulator();
    }

    @Test
    void testPopulate() {
        RegionDto regionDto = new RegionDto();
        regionDto.setCode("code");
        regionDto.setName("name");
        regionDto.setIsoCode("isoCode");

        RegionModel regionModel = new RegionModel();

        regionModelPopulator.populate(regionDto, regionModel);

        assertEquals("code", regionModel.getCode());
        assertEquals("name", regionModel.getName());
        assertEquals("isoCode", regionModel.getIsoCode());
    }
}