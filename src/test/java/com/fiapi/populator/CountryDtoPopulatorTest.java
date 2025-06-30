package com.fiapi.populator;

import com.fiapi.converter.DtoConverter;
import com.fiapi.dto.CountryDto;
import com.fiapi.dto.CurrencyDto;
import com.fiapi.dto.LanguageDto;
import com.fiapi.dto.RegionDto;
import com.fiapi.model.CountryModel;
import com.fiapi.model.CurrencyModel;
import com.fiapi.model.LanguageModel;
import com.fiapi.model.RegionModel;
import com.fiapi.populator.dto.CountryDtoPopulator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CountryDtoPopulatorTest {

    @Mock
    private DtoConverter<LanguageModel, LanguageDto> languageDtoConverter;

    @Mock
    private DtoConverter<CurrencyModel, CurrencyDto> currencyDtoConverter;

    @Mock
    private DtoConverter<RegionModel, RegionDto> regionDtoConverter;

    @InjectMocks
    private CountryDtoPopulator countryDtoPopulator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPopulate() {
        CountryModel countryModel = new CountryModel();
        countryModel.setCode("US");
        countryModel.setIsoCode("USA");
        countryModel.setName("United States");

        LanguageModel languageModel = new LanguageModel();
        LanguageDto languageDto = new LanguageDto();
        when(languageDtoConverter.convert(languageModel)).thenReturn(languageDto);
        countryModel.setLanguage(languageModel);

        CurrencyModel currencyModel = new CurrencyModel();
        CurrencyDto currencyDto = new CurrencyDto();
        when(currencyDtoConverter.convert(currencyModel)).thenReturn(currencyDto);
        countryModel.setCurrency(currencyModel);

        RegionModel regionModel = new RegionModel();
        RegionDto regionDto = new RegionDto();
        List<RegionModel> regionModels = Collections.singletonList(regionModel);
        List<RegionDto> regionDtos = Collections.singletonList(regionDto);
        when(regionDtoConverter.convertAll(regionModels)).thenReturn(regionDtos);
        countryModel.setRegions(regionModels);

        CountryDto countryDto = new CountryDto();
        countryDtoPopulator.populate(countryModel, countryDto);

        assertEquals("US", countryDto.getCode());
        assertEquals("USA", countryDto.getIsoCode());
        assertEquals("United States", countryDto.getName());
    }
}