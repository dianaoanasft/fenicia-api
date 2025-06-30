package com.fiapi.facade;

import com.fiapi.dto.LanguageDto;
import com.fiapi.exception.ConversionException;
import com.fiapi.exception.ModelSaveException;

import java.util.List;
import java.util.Optional;

public interface LanguageFacade {

    void saveLanguage(LanguageDto languageDto) throws ConversionException, ModelSaveException;

    Optional<LanguageDto> getLanguageByCode(String code) throws ConversionException;

    void deleteLanguage(String code) throws ModelSaveException;

    void updateLanguage(LanguageDto languageDto) throws ConversionException, ModelSaveException;

    Optional<LanguageDto> getLanguageByName(String name) throws ConversionException;

    List<LanguageDto> getLanguagesByCountry(String country) throws ConversionException;

}
