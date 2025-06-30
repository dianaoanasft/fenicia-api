package com.fiapi.facade;

import com.fiapi.dto.CurrencyDto;
import com.fiapi.exception.ConversionException;
import com.fiapi.exception.ModelSaveException;

import java.util.List;
import java.util.Optional;

public interface CurrencyFacade {

    void saveCurrency(CurrencyDto languageDto) throws ConversionException, ModelSaveException;

    Optional<CurrencyDto> getCurrencyByCode(String code) throws ConversionException;

    Optional<CurrencyDto> getCurrencyByName(String name) throws ConversionException;

    List<CurrencyDto> getCurrenciesByCountry(String country) throws ConversionException;

    void updateCurrency(CurrencyDto languageDto) throws ConversionException, ModelSaveException;

    void deleteCurrency(String code) throws ModelSaveException;

}
