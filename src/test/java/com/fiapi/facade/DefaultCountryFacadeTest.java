package com.fiapi.facade;

import com.fiapi.converter.DtoConverter;
import com.fiapi.converter.ModelConverter;
import com.fiapi.dto.CountryDto;
import com.fiapi.exception.ModelSaveException;
import com.fiapi.facade.impl.DefaultCountryFacade;
import com.fiapi.model.CountryModel;
import com.fiapi.service.I18NService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class DefaultCountryFacadeTest {

    @Mock
    private I18NService i18NService;

    @Mock
    private ModelConverter<CountryDto, CountryModel> countryModelConverter;

    @Mock
    private DtoConverter<CountryModel, CountryDto> countryDtoConverter;

    @InjectMocks
    private DefaultCountryFacade defaultCountryFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCountry() throws ModelSaveException {
        CountryDto countryDto = new CountryDto();
        CountryModel countryModel = new CountryModel();

        when(countryModelConverter.convert(countryDto)).thenReturn(countryModel);

        defaultCountryFacade.saveCountry(countryDto);

        verify(i18NService, times(1)).saveCountry(countryModel);
    }

    @Test
    void testGetCountryByCode() {
        String code = "US";
        CountryModel countryModel = new CountryModel();
        CountryDto countryDto = new CountryDto();

        when(i18NService.fetchCountryByCode(code)).thenReturn(Optional.of(countryModel));
        when(countryDtoConverter.convert(countryModel)).thenReturn(countryDto);

        Optional<CountryDto> result = defaultCountryFacade.getCountryByCode(code);

        assertTrue(result.isPresent());
        assertEquals(countryDto, result.get());
    }

    @Test
    void testGetCountryByIsoCode() {
        String isoCode = "USA";
        CountryModel countryModel = new CountryModel();
        CountryDto countryDto = new CountryDto();

        when(i18NService.fetchCountryByIsoCode(isoCode)).thenReturn(Optional.of(countryModel));
        when(countryDtoConverter.convert(countryModel)).thenReturn(countryDto);

        Optional<CountryDto> result = defaultCountryFacade.getCountryByIsoCode(isoCode);

        assertTrue(result.isPresent());
        assertEquals(countryDto, result.get());
    }

    @Test
    void testGetCountryByName() {
        String name = "United States";
        CountryModel countryModel = new CountryModel();
        CountryDto countryDto = new CountryDto();

        when(i18NService.fetchCountryByName(name)).thenReturn(Optional.of(countryModel));
        when(countryDtoConverter.convert(countryModel)).thenReturn(countryDto);

        Optional<CountryDto> result = defaultCountryFacade.getCountryByName(name);

        assertTrue(result.isPresent());
        assertEquals(countryDto, result.get());
    }

}