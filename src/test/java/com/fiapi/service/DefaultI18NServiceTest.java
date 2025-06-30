package com.fiapi.service;

import com.fiapi.exception.ModelSaveException;
import com.fiapi.model.CountryModel;
import com.fiapi.model.CurrencyModel;
import com.fiapi.model.LanguageModel;
import com.fiapi.model.RegionModel;
import com.fiapi.repository.CountryRepository;
import com.fiapi.repository.CurrencyRepository;
import com.fiapi.repository.LanguageRepository;
import com.fiapi.repository.RegionRepository;
import com.fiapi.service.impl.DefaultI18NService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DefaultI18NServiceTest {

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private CurrencyRepository currencyRepository;

    @Mock
    private LanguageRepository languageRepository;

    @Mock
    private RegionRepository regionRepository;

    @InjectMocks
    private DefaultI18NService i18NService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFetchAllCountries() {
        when(countryRepository.findAll()).thenReturn(Collections.emptyList());
        assertThat(i18NService.fetchAllCountries()).isEmpty();
    }

    @Test
    void shouldFetchCountryByCode() {
        CountryModel country = new CountryModel();
        when(countryRepository.findByCode("code")).thenReturn(country);
        assertThat(i18NService.fetchCountryByCode("code")).contains(country);
    }

    @Test
    void shouldSaveCountrySuccessfully() throws ModelSaveException {
        CountryModel country = new CountryModel();
        CountryModel savedCountry = new CountryModel();
        savedCountry.setPk(1L);
        when(countryRepository.save(country)).thenReturn(savedCountry);
        i18NService.saveCountry(country);
        verify(countryRepository, times(1)).save(country);
    }

    @Test
    void shouldThrowModelSaveException_whenSavingCountryFails() {
        CountryModel country = new CountryModel();
        when(countryRepository.save(country)).thenReturn(country);
        assertThatThrownBy(() -> i18NService.saveCountry(country))
                .isInstanceOf(ModelSaveException.class)
                .hasMessageContaining("Failed to save country");
    }

    @Test
    void shouldFetchAllCurrencies() {
        when(currencyRepository.findAll()).thenReturn(Collections.emptyList());
        assertThat(i18NService.fetchAllCurrencies()).isEmpty();
    }

    @Test
    void shouldFetchCurrencyByCode() {
        CurrencyModel currency = new CurrencyModel();
        when(currencyRepository.findByCode("code")).thenReturn(currency);
        assertThat(i18NService.fetchCurrencyByCode("code")).contains(currency);
    }

    @Test
    void shouldSaveCurrencySuccessfully() throws ModelSaveException {
        CurrencyModel currency = new CurrencyModel();
        CurrencyModel savedCurrency = new CurrencyModel();
        savedCurrency.setPk(1L);
        when(currencyRepository.save(currency)).thenReturn(savedCurrency);
        i18NService.saveCurrency(currency);
        verify(currencyRepository, times(1)).save(currency);
    }

    @Test
    void shouldThrowModelSaveException_whenSavingCurrencyFails() {
        CurrencyModel currency = new CurrencyModel();
        when(currencyRepository.save(currency)).thenReturn(currency);
        assertThatThrownBy(() -> i18NService.saveCurrency(currency))
                .isInstanceOf(ModelSaveException.class)
                .hasMessageContaining("Failed to save currency");
    }

    @Test
    void shouldFetchAllLanguages() {
        when(languageRepository.findAll()).thenReturn(Collections.emptyList());
        assertThat(i18NService.fetchAllLanguages()).isEmpty();
    }

    @Test
    void shouldFetchLanguageByCode() {
        LanguageModel language = new LanguageModel();
        when(languageRepository.findByCode("code")).thenReturn(language);
        assertThat(i18NService.fetchLanguageByCode("code")).contains(language);
    }

    @Test
    void shouldSaveLanguageSuccessfully() throws ModelSaveException {
        LanguageModel language = new LanguageModel();
        LanguageModel savedLanguage = new LanguageModel();
        savedLanguage.setPk(1L);
        when(languageRepository.save(language)).thenReturn(savedLanguage);
        i18NService.saveLanguage(language);
        verify(languageRepository, times(1)).save(language);
    }

    @Test
    void shouldThrowModelSaveException_whenSavingLanguageFails() {
        LanguageModel language = new LanguageModel();
        when(languageRepository.save(language)).thenReturn(language);
        assertThatThrownBy(() -> i18NService.saveLanguage(language))
                .isInstanceOf(ModelSaveException.class)
                .hasMessageContaining("Failed to save language");
    }

    @Test
    void shouldDeleteLanguageSuccessfully() throws ModelSaveException {
        LanguageModel language = new LanguageModel();
        when(languageRepository.findByCode("code")).thenReturn(language);
        i18NService.deleteLanguage("code");
        verify(languageRepository, times(1)).delete(language);
    }

    @Test
    void shouldThrowModelSaveException_whenDeletingLanguageFails() {
        when(languageRepository.findByCode("code")).thenReturn(null);
        assertThatThrownBy(() -> i18NService.deleteLanguage("code"))
                .isInstanceOf(ModelSaveException.class)
                .hasMessageContaining("Language not found");
    }

    @Test
    void shouldFetchAllRegions() {
        when(regionRepository.findAll()).thenReturn(Collections.emptyList());
        assertThat(i18NService.fetchAllRegions()).isEmpty();
    }

    @Test
    void shouldFetchRegionByCode() {
        RegionModel region = new RegionModel();
        when(regionRepository.findByCode("code")).thenReturn(region);
        assertThat(i18NService.fetchRegionByCode("code")).contains(region);
    }

    @Test
    void shouldSaveRegionSuccessfully() throws ModelSaveException {
        RegionModel region = new RegionModel();
        RegionModel savedRegion = new RegionModel();
        savedRegion.setPk(1L);
        when(regionRepository.save(region)).thenReturn(savedRegion);
        i18NService.saveRegion(region);
        verify(regionRepository, times(1)).save(region);
    }

    @Test
    void shouldThrowModelSaveException_whenSavingRegionFails() {
        RegionModel region = new RegionModel();
        when(regionRepository.save(region)).thenReturn(region);
        assertThatThrownBy(() -> i18NService.saveRegion(region))
                .isInstanceOf(ModelSaveException.class)
                .hasMessageContaining("Failed to save region");
    }
}