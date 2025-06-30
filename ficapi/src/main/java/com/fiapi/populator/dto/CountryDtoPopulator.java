package com.fiapi.populator.dto;

import com.fiapi.converter.DtoConverter;
import com.fiapi.dto.CountryDto;
import com.fiapi.dto.CurrencyDto;
import com.fiapi.dto.LanguageDto;
import com.fiapi.dto.RegionDto;
import com.fiapi.model.CountryModel;
import com.fiapi.model.CurrencyModel;
import com.fiapi.model.LanguageModel;
import com.fiapi.model.RegionModel;
import com.fiapi.populator.base.DtoPopulator;

import java.util.List;
import java.util.Objects;

public class CountryDtoPopulator implements DtoPopulator<CountryModel, CountryDto> {

    private final DtoConverter<LanguageModel, LanguageDto> languageDtoConverter;
    private final DtoConverter<CurrencyModel, CurrencyDto> currencyDtoConverter;
    private final DtoConverter<RegionModel, RegionDto> regionDtoConverter;

    public CountryDtoPopulator(DtoConverter<LanguageModel, LanguageDto> languageDtoConverter,
                               DtoConverter<CurrencyModel, CurrencyDto> currencyDtoConverter, DtoConverter<RegionModel, RegionDto> regionDtoConverter) {
        this.languageDtoConverter = languageDtoConverter;
        this.currencyDtoConverter = currencyDtoConverter;
        this.regionDtoConverter = regionDtoConverter;
    }

    @Override
    public void populate(CountryModel source, CountryDto target) {
        target.setCode(source.getCode());
        target.setIsoCode(source.getIsoCode());
        target.setName(source.getName());

        populateRegions(source.getRegions(), target);
        populateLanguages(source.getLanguage(), target);
        populateCurrencies(source.getCurrency(), target);
    }

    private void populateCurrencies(CurrencyModel currency, CountryDto target) {
        if (Objects.nonNull(currency)) {
            target.setCurrency(currencyDtoConverter.convert(currency));
        }
    }

    private void populateLanguages(LanguageModel language, CountryDto target) {
        target.setLanguage(languageDtoConverter.convert(language));
    }

    private void populateRegions(List<RegionModel> regions, CountryDto target) {
        target.setRegions(regionDtoConverter.convertAll(regions));
    }

}
