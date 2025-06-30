package com.fiapi.facade.impl;

import com.fiapi.converter.DtoConverter;
import com.fiapi.converter.ModelConverter;
import com.fiapi.dto.CurrencyDto;
import com.fiapi.exception.ConversionException;
import com.fiapi.exception.ModelSaveException;
import com.fiapi.facade.CurrencyFacade;
import com.fiapi.model.CurrencyModel;
import com.fiapi.service.I18NService;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public class DefaultCurrencyFacade implements CurrencyFacade {

    private final I18NService i18NService;
    private final ModelConverter<CurrencyDto, CurrencyModel> currencyModelConverter;
    private final DtoConverter<CurrencyModel, CurrencyDto> currencyDtoConverter;

    public DefaultCurrencyFacade(I18NService i18NService, ModelConverter<CurrencyDto, CurrencyModel> currencyModelConverter, DtoConverter<CurrencyModel, CurrencyDto> currencyDtoConverter) {
        this.i18NService = i18NService;
        this.currencyModelConverter = currencyModelConverter;
        this.currencyDtoConverter = currencyDtoConverter;
    }

    @Override
    public void saveCurrency(@NotNull CurrencyDto currencyDto) throws ConversionException, ModelSaveException {
        final CurrencyModel currency = currencyModelConverter.convert(currencyDto);
        i18NService.saveCurrency(currency);
    }

    @Override
    public Optional<CurrencyDto> getCurrencyByCode(String code) throws ConversionException {
        return i18NService
                .fetchCurrencyByCode(code)
                .map(currencyDtoConverter::convert);
    }

    @Override
    public Optional<CurrencyDto> getCurrencyByName(String name) throws ConversionException {
        return i18NService.fetchCurrencyByName(name)
                .map(currencyDtoConverter::convert);
    }

    @Override
    public List<CurrencyDto> getCurrenciesByCountry(String country) throws ConversionException {
        return i18NService.fetchCurrencyByCountry(country).stream()
                .map(currencyDtoConverter::convert)
                .toList();
    }

    @Override
    public void updateCurrency(CurrencyDto languageDto) throws ConversionException, ModelSaveException {
        final CurrencyModel currencyModel = i18NService.fetchCurrencyByCode(languageDto.getCode())
                .orElseThrow(() -> new ModelSaveException("Currency not found"));

        currencyModel.setName(languageDto.getName());
        i18NService.saveCurrency(currencyModel);
    }

    @Override
    public void deleteCurrency(String code) throws ModelSaveException {
        i18NService.deleteCurrency(code);
    }

}
