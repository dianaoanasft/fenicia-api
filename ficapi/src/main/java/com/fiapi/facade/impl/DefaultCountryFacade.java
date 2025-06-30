package com.fiapi.facade.impl;

import com.fiapi.converter.DtoConverter;
import com.fiapi.converter.ModelConverter;
import com.fiapi.dto.CountryDto;
import com.fiapi.exception.ModelSaveException;
import com.fiapi.facade.CountryFacade;
import com.fiapi.model.CountryModel;
import com.fiapi.service.I18NService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DefaultCountryFacade implements CountryFacade {

    private final I18NService i18NService;
    private final ModelConverter<CountryDto, CountryModel> countryModelConverter;
    private final DtoConverter<CountryModel, CountryDto> countryDtoConverter;

    public DefaultCountryFacade(I18NService i18NService, ModelConverter<CountryDto, CountryModel> countryModelConverter,
                                DtoConverter<CountryModel, CountryDto> countryDtoConverter) {
        this.i18NService = i18NService;
        this.countryModelConverter = countryModelConverter;
        this.countryDtoConverter = countryDtoConverter;
    }

    @Override
    public List<CountryDto> getAllCountries() {
        List<CountryModel> countryModels = i18NService.fetchAllCountries();
        if (CollectionUtils.isNotEmpty(countryModels)) {
            return countryModels.stream()
                    .map(countryDtoConverter::convert)
                    .toList();
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<CountryDto> getCountryByCode(@NotBlank String code) {
        Optional<CountryModel> optionalCountry = i18NService.fetchCountryByCode(code);
        return getCountryDto(optionalCountry);
    }

    @Override
    public Optional<CountryDto> getCountryByIsoCode(@NotBlank String isoCode) {
        Optional<CountryModel> optionalCountry = i18NService.fetchCountryByIsoCode(isoCode);
        return getCountryDto(optionalCountry);
    }

    @Override
    public Optional<CountryDto> getCountryByName(@NotBlank String name) {
        Optional<CountryModel> optionalCountry = i18NService.fetchCountryByName(name);
        return getCountryDto(optionalCountry);
    }

    @Override
    @Transactional
    public void saveCountry(@NotNull CountryDto countryDto) throws ModelSaveException {
        try {
            final CountryModel countryModel = countryModelConverter.convert(countryDto);
            i18NService.saveCountry(countryModel);
        } catch (Exception e) {
            throw new ModelSaveException("Error saving country", e);
        }
    }

    private Optional<CountryDto> getCountryDto(Optional<CountryModel> optionalCountry) {
        if (optionalCountry.isPresent()) {
            final CountryDto countryDto = convertCountryModelToDto(optionalCountry);
            return Optional.of(countryDto);
        }
        return Optional.empty();
    }

    @NotNull
    private CountryDto convertCountryModelToDto(Optional<CountryModel> optionalCountry) {
        return optionalCountry
                .map(countryDtoConverter::convert)
                .orElseThrow(() -> new IllegalArgumentException("CountryModel is not present"));
    }

}