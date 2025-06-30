package com.fiapi.service;

import com.fiapi.exception.ModelSaveException;
import com.fiapi.model.CountryModel;
import com.fiapi.model.CurrencyModel;
import com.fiapi.model.LanguageModel;
import com.fiapi.model.RegionModel;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface I18NService {

    // Country related methods

    List<CountryModel> fetchAllCountries();

    Optional<CountryModel> fetchCountryByCode(String code);

    Optional<CountryModel> fetchCountryByIsoCode(String isoCode);

    Optional<CountryModel> fetchCountryByName(String name);

    List<CountryModel> fetchCountriesByRegion(RegionModel region);

    List<CountryModel> fetchCountriesByLanguage(LanguageModel language);

    List<CountryModel> fetchCountriesByCurrency(CurrencyModel currency);

    void saveCountry(CountryModel countryModel) throws ModelSaveException;

    // Currency related methods

    List<CurrencyModel> fetchAllCurrencies();

    Optional<CurrencyModel> fetchCurrencyByCode(String code);

    Optional<CurrencyModel> fetchCurrencyByName(String isoCode);

    List<CurrencyModel> fetchCurrencyByCountry(String countryCode);

    void saveCurrency(CurrencyModel currencyModel) throws ModelSaveException;

    void deleteCurrency(String code) throws ModelSaveException;

    // Language related methods

    List<LanguageModel> fetchAllLanguages();

    Optional<LanguageModel> fetchLanguageByCode(String code);

    Optional<LanguageModel> fetchLanguageByName(String name);

    List<LanguageModel> fetchLanguageByCountry(String countryCode);

    void saveLanguage(LanguageModel languageModel) throws ModelSaveException;

    void deleteLanguage(String code) throws ModelSaveException;

    // Region related methods

    List<RegionModel> fetchAllRegions();

    Optional<RegionModel> fetchRegionByCode(String code);

    Optional<RegionModel> fetchRegionByName(String name);

    Optional<RegionModel> fetchRegionByIsoCode(String isoCode);

    List<RegionModel> fetchRegionsByCountry(String country);

    void saveRegion(RegionModel regionModel) throws ModelSaveException;

    void deleteRegion(String code) throws ModelSaveException;

}
