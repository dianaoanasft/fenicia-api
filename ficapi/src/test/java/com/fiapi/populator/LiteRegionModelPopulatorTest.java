package com.fiapi.populator;

import com.fiapi.dto.RegionDto;
import com.fiapi.model.RegionModel;
import com.fiapi.populator.base.ModelPopulator;
import com.fiapi.populator.model.RegionModelPopulator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class LiteRegionModelPopulatorTest {

    private ModelPopulator<RegionDto, RegionModel> underTest;

    @BeforeEach
    void setUp() {
        underTest = new RegionModelPopulator();
    }

    @Test
    void shouldPopulateRegionModel_when() {
        RegionDto regionDto = new RegionDto();
        regionDto.setCode("code");
        regionDto.setName("name");
        regionDto.setIsoCode("isoCode");

        RegionModel regionModel = new RegionModel();

        underTest.populate(regionDto, regionModel);

        assertThat(regionModel.getCode()).isEqualTo("code");
        assertThat(regionModel.getName()).isEqualTo("name");
        assertThat(regionModel.getIsoCode()).isEqualTo("isoCode");
    }

}
