package com.fiapi.populator.model;

import com.fiapi.converter.ModelConverter;
import com.fiapi.dto.CountryDto;
import com.fiapi.dto.CurrencyDto;
import com.fiapi.dto.LanguageDto;
import com.fiapi.dto.RegionDto;
import com.fiapi.exception.ConversionException;
import com.fiapi.model.CountryModel;
import com.fiapi.model.CurrencyModel;
import com.fiapi.model.LanguageModel;
import com.fiapi.model.RegionModel;
import com.fiapi.populator.base.ModelPopulator;
import com.fiapi.service.I18NService;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CountryModelPopulator implements ModelPopulator<CountryDto, CountryModel> {

    private final I18NService i18NService;

    private final ModelConverter<RegionDto, RegionModel> regionModelConverter;
    private final ModelConverter<LanguageDto, LanguageModel> languageModelConverter;
    private final ModelConverter<CurrencyDto, CurrencyModel> currencyModelConverter;

    public CountryModelPopulator(I18NService i18NService, ModelConverter<RegionDto, RegionModel> regionModelConverter,
                                 ModelConverter<LanguageDto, LanguageModel> languageModelConverter,
                                 ModelConverter<CurrencyDto, CurrencyModel> currencyModelConverter) {
        this.i18NService = i18NService;
        this.regionModelConverter = regionModelConverter;
        this.languageModelConverter = languageModelConverter;
        this.currencyModelConverter = currencyModelConverter;
    }

    @Override
    public void populate(CountryDto source, CountryModel target) {
        validateParams(source.getCode(), source.getIsoCode(), source.getName(),
                source.getLanguage(), source.getCurrency(), source.getRegions());

        try {
            target.setCode(source.getCode());
            target.setIsoCode(source.getIsoCode());
            target.setName(source.getName());

            i18NService.saveCountry(target);

            final List<RegionModel> regions = getRegions(source);
            final LanguageModel language = getLanguage(source);
            final CurrencyModel currency = getCurrency(source);

            regions.forEach(region -> region.setCountry(target));

            target.setRegions(regions);
            target.setLanguage(language);
            target.setCurrency(currency);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<RegionModel> getRegions(CountryDto source) {
        final List<RegionModel> regions = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(source.getRegions())) {
            List<RegionDto> regionsDto = source.getRegions();
            for (RegionDto regionDto : regionsDto) {
                i18NService.fetchRegionByCode(regionDto.getCode())
                        .ifPresentOrElse(regions::add, () -> {
                            RegionModel region = regionModelConverter.convert(regionDto);
                            regions.add(region);
                        });
            }
        }
        return regions;
    }

    private LanguageModel getLanguage(CountryDto source) {
        if (Objects.nonNull(source.getLanguage())) {
            final LanguageDto languageDto = source.getLanguage();
            Optional<LanguageModel> languageModelOptional = i18NService.fetchLanguageByCode(languageDto.getCode());
            return languageModelOptional.orElseGet(() -> languageModelConverter.convert(languageDto));
        }
        return null;
    }

    private CurrencyModel getCurrency(CountryDto source) {
        if (Objects.nonNull(source.getCurrency())) {
            final CurrencyDto currency = source.getCurrency();
            Optional<CurrencyModel> currencyModelOptional = i18NService.fetchCurrencyByCode(currency.getCode());
            return currencyModelOptional.orElseGet(() -> currencyModelConverter.convert(currency));
        }
        return null;
    }

    private void validateParams(Object... params) throws ConversionException {
        for (Object param : params) {
            if (Objects.isNull((param))) {
                throw new ConversionException("Invalid parameter");
            }
        }
    }

}