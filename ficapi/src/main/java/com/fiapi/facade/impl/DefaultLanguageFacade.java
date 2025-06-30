package com.fiapi.facade.impl;

import com.fiapi.converter.DtoConverter;
import com.fiapi.converter.ModelConverter;
import com.fiapi.dto.LanguageDto;
import com.fiapi.exception.ConversionException;
import com.fiapi.exception.ModelSaveException;
import com.fiapi.facade.LanguageFacade;
import com.fiapi.model.LanguageModel;
import com.fiapi.service.I18NService;
import jakarta.validation.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public class DefaultLanguageFacade implements LanguageFacade {

    private final I18NService i18NService;
    private final ModelConverter<LanguageDto, LanguageModel> languageModelConverter;
    private final DtoConverter<LanguageModel, LanguageDto> languageDtoDtoConverter;

    public DefaultLanguageFacade(I18NService i18NService, ModelConverter<LanguageDto, LanguageModel> languageModelConverter, DtoConverter<LanguageModel, LanguageDto> languageDtoDtoConverter) {
        this.i18NService = i18NService;
        this.languageModelConverter = languageModelConverter;
        this.languageDtoDtoConverter = languageDtoDtoConverter;
    }

    @Override
    public void saveLanguage(@NotNull LanguageDto languageDto) throws ConversionException, ModelSaveException {
        final LanguageModel language = languageModelConverter.convert(languageDto);
        i18NService.saveLanguage(language);
    }

    @Override
    public Optional<LanguageDto> getLanguageByCode(@NotBlank String code) throws ConversionException {
        return i18NService
                .fetchLanguageByCode(code)
                .map(languageDtoDtoConverter::convert);
    }

    @Override
    public void deleteLanguage(String code) throws ModelSaveException {
        i18NService.deleteLanguage(code);
    }

    @Override
    public void updateLanguage(LanguageDto languageDto) throws ConversionException, ModelSaveException {
        final LanguageModel languageModel = i18NService.fetchLanguageByCode(languageDto.getCode())
                .orElseThrow(() -> new ModelSaveException("Language not found"));

        languageModel.setName(languageDto.getName());
        i18NService.saveLanguage(languageModel);
    }

    @Override
    public Optional<LanguageDto> getLanguageByName(String name) throws ConversionException {
        return i18NService
                .fetchLanguageByName(name)
                .map(languageDtoDtoConverter::convert);
    }

    @Override
    public List<LanguageDto> getLanguagesByCountry(String country) throws ConversionException {
        return i18NService
                .fetchLanguageByCountry(country)
                .stream().map(languageDtoDtoConverter::convert)
                .toList();
    }

}
