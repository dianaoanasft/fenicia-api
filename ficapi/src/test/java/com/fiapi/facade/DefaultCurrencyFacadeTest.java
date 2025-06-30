package com.fiapi.facade;

import com.fiapi.converter.DtoConverter;
import com.fiapi.converter.ModelConverter;
import com.fiapi.dto.CurrencyDto;
import com.fiapi.exception.ConversionException;
import com.fiapi.exception.ModelSaveException;
import com.fiapi.facade.impl.DefaultCurrencyFacade;
import com.fiapi.model.CurrencyModel;
import com.fiapi.service.I18NService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DefaultCurrencyFacadeTest {

    @Mock
    private I18NService i18NService;

    @Mock
    private ModelConverter<CurrencyDto, CurrencyModel> currencyModelConverter;

    @Mock
    private DtoConverter<CurrencyModel, CurrencyDto> currencyDtoConverter;

    @InjectMocks
    private DefaultCurrencyFacade defaultCurrencyFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCurrency() throws ConversionException, ModelSaveException {
        CurrencyDto currencyDto = new CurrencyDto();
        CurrencyModel currencyModel = new CurrencyModel();

        when(currencyModelConverter.convert(currencyDto)).thenReturn(currencyModel);

        defaultCurrencyFacade.saveCurrency(currencyDto);

        verify(i18NService, times(1)).saveCurrency(currencyModel);
    }

    @Test
    void testGetCurrencyByCode() throws ConversionException {
        String code = "USD";
        CurrencyModel currencyModel = new CurrencyModel();
        CurrencyDto currencyDto = new CurrencyDto();

        when(i18NService.fetchCurrencyByCode(code)).thenReturn(Optional.of(currencyModel));
        when(currencyDtoConverter.convert(currencyModel)).thenReturn(currencyDto);

        Optional<CurrencyDto> result = defaultCurrencyFacade.getCurrencyByCode(code);

        assertTrue(result.isPresent());
        assertEquals(currencyDto, result.get());
    }

    @Test
    void testGetCurrencyByName() throws ConversionException {
        String name = "Dollar";
        CurrencyModel currencyModel = new CurrencyModel();
        CurrencyDto currencyDto = new CurrencyDto();

        when(i18NService.fetchCurrencyByName(name)).thenReturn(Optional.of(currencyModel));
        when(currencyDtoConverter.convert(currencyModel)).thenReturn(currencyDto);

        Optional<CurrencyDto> result = defaultCurrencyFacade.getCurrencyByName(name);

        assertTrue(result.isPresent());
        assertEquals(currencyDto, result.get());
    }

    @Test
    void testDeleteCurrency() throws ModelSaveException {
        String code = "USD";

        doNothing().when(i18NService).deleteCurrency(code);

        defaultCurrencyFacade.deleteCurrency(code);

        verify(i18NService, times(1)).deleteCurrency(code);
    }
}