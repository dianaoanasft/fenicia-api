package com.fiapi.facade;

import com.fiapi.converter.DtoConverter;
import com.fiapi.converter.ModelConverter;
import com.fiapi.dto.LanguageDto;
import com.fiapi.exception.ConversionException;
import com.fiapi.exception.ModelSaveException;
import com.fiapi.facade.impl.DefaultLanguageFacade;
import com.fiapi.model.LanguageModel;
import com.fiapi.service.I18NService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DefaultLanguageFacadeTest {

    @Mock
    private I18NService i18NService;

    @Mock
    private ModelConverter<LanguageDto, LanguageModel> languageModelConverter;

    @Mock
    private DtoConverter<LanguageModel, LanguageDto> languageDtoDtoConverter;

    @InjectMocks
    private DefaultLanguageFacade languageFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveLanguageSuccessfully() throws ConversionException, ModelSaveException {
        LanguageDto languageDto = new LanguageDto();
        LanguageModel languageModel = new LanguageModel();

        when(languageModelConverter.convert(languageDto)).thenReturn(languageModel);
        doNothing().when(i18NService).saveLanguage(languageModel);

        languageFacade.saveLanguage(languageDto);

        verify(languageModelConverter, times(1)).convert(languageDto);
        verify(i18NService, times(1)).saveLanguage(languageModel);
    }

    @Test
    void shouldThrowException_whenSaveLanguageFails() throws ConversionException, ModelSaveException {
        LanguageDto languageDto = new LanguageDto();
        LanguageModel languageModel = new LanguageModel();

        when(languageModelConverter.convert(languageDto)).thenReturn(languageModel);
        doThrow(new ModelSaveException("Failed to save language")).when(i18NService).saveLanguage(languageModel);

        assertThatThrownBy(() -> languageFacade.saveLanguage(languageDto))
                .isInstanceOf(ModelSaveException.class)
                .hasMessageContaining("Failed to save language");

        verify(languageModelConverter, times(1)).convert(languageDto);
        verify(i18NService, times(1)).saveLanguage(languageModel);
    }

    @Test
    void shouldGetLanguageByCodeSuccessfully() throws ConversionException {
        String code = "en";
        LanguageModel languageModel = new LanguageModel();
        LanguageDto languageDto = new LanguageDto();

        when(i18NService.fetchLanguageByCode(code)).thenReturn(Optional.of(languageModel));
        when(languageDtoDtoConverter.convert(languageModel)).thenReturn(languageDto);

        Optional<LanguageDto> result = languageFacade.getLanguageByCode(code);

        assertThat(result).isPresent().contains(languageDto);
        verify(i18NService, times(1)).fetchLanguageByCode(code);
        verify(languageDtoDtoConverter, times(1)).convert(languageModel);
    }

    @Test
    void shouldReturnEmptyOptional_whenLanguageNotFound() throws ConversionException {
        String code = "en";

        when(i18NService.fetchLanguageByCode(code)).thenReturn(Optional.empty());

        Optional<LanguageDto> result = languageFacade.getLanguageByCode(code);

        assertThat(result).isEmpty();
        verify(i18NService, times(1)).fetchLanguageByCode(code);
        verify(languageDtoDtoConverter, never()).convert(any(LanguageModel.class));
    }

    @Test
    void shouldDeleteLanguageSuccessfully() throws ModelSaveException {
        String code = "en";

        doNothing().when(i18NService).deleteLanguage(code);

        languageFacade.deleteLanguage(code);

        verify(i18NService, times(1)).deleteLanguage(code);
    }

    @Test
    void shouldThrowException_whenDeleteLanguageFails() throws ModelSaveException {
        String code = "en";

        doThrow(new ModelSaveException("Failed to delete language")).when(i18NService).deleteLanguage(code);

        assertThatThrownBy(() -> languageFacade.deleteLanguage(code))
                .isInstanceOf(ModelSaveException.class)
                .hasMessageContaining("Failed to delete language");

        verify(i18NService, times(1)).deleteLanguage(code);
    }
}
