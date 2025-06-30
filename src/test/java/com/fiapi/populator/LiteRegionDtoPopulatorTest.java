package com.fiapi.populator;

import com.fiapi.dto.RegionDto;
import com.fiapi.model.RegionModel;
import com.fiapi.populator.base.DtoPopulator;
import com.fiapi.populator.dto.RegionDtoPopulator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class LiteRegionDtoPopulatorTest {

    private DtoPopulator<RegionModel, RegionDto> underTest;

    @BeforeEach
    void setUp() {
        underTest = new RegionDtoPopulator();
    }

    @Test
    void shouldPopulateRegionDto() {
        RegionModel regionModel = new RegionModel();
        regionModel.setCode("code");
        regionModel.setName("name");
        regionModel.setIsoCode("isoCode");

        RegionDto regionDto = new RegionDto();
        underTest.populate(regionModel, regionDto);

        assertThat(regionDto.getCode()).isEqualTo("code");
        assertThat(regionDto.getName()).isEqualTo("name");
        assertThat(regionDto.getIsoCode()).isEqualTo("isoCode");
    }

}
