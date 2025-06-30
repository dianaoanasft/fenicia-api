package com.fiapi.service.impl;

import com.fiapi.exception.ModelSaveException;
import com.fiapi.model.CountryModel;
import com.fiapi.model.CurrencyModel;
import com.fiapi.model.LanguageModel;
import com.fiapi.model.RegionModel;
import com.fiapi.repository.CountryRepository;
import com.fiapi.repository.CurrencyRepository;
import com.fiapi.repository.LanguageRepository;
import com.fiapi.repository.RegionRepository;
import com.fiapi.service.I18NService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DefaultI18NService implements I18NService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultI18NService.class);

    private final CountryRepository countryRepository;
    private final CurrencyRepository currencyRepository;
    private final LanguageRepository languageRepository;
    private final RegionRepository regionRepository;

    public DefaultI18NService(CountryRepository countryRepository, CurrencyRepository currencyRepository,
                              LanguageRepository languageRepository, RegionRepository regionRepository) {
        this.countryRepository = countryRepository;
        this.currencyRepository = currencyRepository;
        this.languageRepository = languageRepository;
        this.regionRepository = regionRepository;
    }

    @Override
    public List<CountryModel> fetchAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Optional<CountryModel> fetchCountryByCode(@NonNull String code) {
        return Optional.ofNullable(countryRepository.findByCode(code));
    }

    @Override
    public Optional<CountryModel> fetchCountryByIsoCode(@NonNull String isoCode) {
        return Optional.ofNullable(countryRepository.findByIsoCode(isoCode));
    }

    @Override
    public Optional<CountryModel> fetchCountryByName(@NonNull String name) {
        return Optional.ofNullable(countryRepository.findByName(name));
    }

    @Override
    public List<CountryModel> fetchCountriesByRegion(@NonNull RegionModel region) {
        return countryRepository.findByRegion(region);
    }

    @Override
    public List<CountryModel> fetchCountriesByLanguage(@NonNull LanguageModel language) {
        return countryRepository.findByLanguage(language);
    }

    @Override
    public List<CountryModel> fetchCountriesByCurrency(@NonNull CurrencyModel currency) {
        return countryRepository.findByCurrency(currency);
    }

    @Override
    public void saveCountry(@NonNull CountryModel country) throws ModelSaveException {
        if (Objects.isNull(country.getPk())) {
            logger.warn("Country is new {}, generating new primary key", country.getCode());
        }
        final CountryModel savedCountry = countryRepository.save(country);
        if (Objects.isNull(savedCountry.getPk())) {
            throw new ModelSaveException("Failed to save country");
        }
        logger.info("Country saved successfully: {}", savedCountry.getCode());
    }

    @Override
    public List<CurrencyModel> fetchAllCurrencies() {
        return currencyRepository.findAll();
    }

    @Override
    public Optional<CurrencyModel> fetchCurrencyByCode(@NonNull String code) {
        return Optional.ofNullable(currencyRepository.findByCode(code));
    }

    @Override
    public Optional<CurrencyModel> fetchCurrencyByName(@NonNull String isoCode) {
        return Optional.ofNullable(currencyRepository.findByName(isoCode));
    }

    @Override
    public List<CurrencyModel> fetchCurrencyByCountry(@NonNull String countryCode) {
        final CountryModel country = countryRepository.findByCode(countryCode);
        return currencyRepository.findByCountry(country);
    }

    @Override
    public void saveCurrency(@NonNull CurrencyModel currencyModel) throws ModelSaveException {
        if (Objects.isNull(currencyModel.getPk())) {
            logger.warn("Currency is new {}, generating new primary key", currencyModel.getCode());
        }
        final CurrencyModel savedCurrency = currencyRepository.save(currencyModel);
        if (Objects.isNull(savedCurrency.getPk())) {
            throw new ModelSaveException("Failed to save currency");
        }
        logger.info("Currency saved successfully: {}", savedCurrency.getCode());
    }

    @Override
    public void deleteCurrency(String code) throws ModelSaveException {
        final CurrencyModel currency = currencyRepository.findByCode(code);
        if (Objects.isNull(currency)) {
            throw new ModelSaveException("Currency not found");
        }
        currencyRepository.delete(currency);
    }

    @Override
    public List<LanguageModel> fetchAllLanguages() {
        return languageRepository.findAll();
    }

    @Override
    public Optional<LanguageModel> fetchLanguageByCode(@NonNull String code) {
        return Optional.ofNullable(languageRepository.findByCode(code));
    }

    @Override
    public Optional<LanguageModel> fetchLanguageByName(@NonNull String name) {
        return Optional.ofNullable(languageRepository.findByName(name));
    }

    @Override
    public List<LanguageModel> fetchLanguageByCountry(@NonNull String countryCode) {
        final CountryModel country = countryRepository.findByCode(countryCode);
        return languageRepository.findByCountry(country);
    }

    @Override
    public void saveLanguage(@NonNull LanguageModel languageModel) throws ModelSaveException {
        if (Objects.isNull(languageModel.getPk())) {
            logger.warn("Language is new {}, generating new primary key", languageModel.getCode());
        }
        final LanguageModel savedLanguage = languageRepository.save(languageModel);
        if (Objects.isNull(savedLanguage.getPk())) {
            throw new ModelSaveException("Failed to save language");
        }
        logger.info("Language saved successfully: {}", savedLanguage.getCode());
    }

    @Override
    public void deleteLanguage(String code) throws ModelSaveException {
        final LanguageModel language = languageRepository.findByCode(code);
        if (Objects.isNull(language)) {
            throw new ModelSaveException("Language not found");
        }
        languageRepository.delete(language);
        logger.info("Language deleted successfully: {}", code);
    }

    @Override
    public List<RegionModel> fetchAllRegions() {
        return regionRepository.findAll();
    }

    @Override
    public Optional<RegionModel> fetchRegionByCode(@NonNull String code) {
        return Optional.ofNullable(regionRepository.findByCode(code));
    }

    @Override
    public Optional<RegionModel> fetchRegionByName(@NonNull String name) {
        return Optional.ofNullable(regionRepository.findByName(name));
    }

    @Override
    public Optional<RegionModel> fetchRegionByIsoCode(@NonNull String isoCode) {
        return Optional.ofNullable(regionRepository.findByIsoCode(isoCode));
    }

    @Override
    public List<RegionModel> fetchRegionsByCountry(@NonNull String country) {
        final CountryModel countryModel = countryRepository.findByCode(country);
        return regionRepository.findByCountry(countryModel);
    }

    @Override
    public void saveRegion(@NonNull RegionModel regionModel) throws ModelSaveException {
        if (Objects.isNull(regionModel.getPk())) {
            logger.warn("Region is new {}, generating new primary key", regionModel.getCode());
        }
        final RegionModel savedRegion = regionRepository.save(regionModel);
        if (Objects.isNull(savedRegion.getPk())) {
            throw new ModelSaveException("Failed to save region");
        }
        logger.info("Region saved successfully: {}", savedRegion.getCode());
    }

    @Override
    public void deleteRegion(String code) throws ModelSaveException {
        final RegionModel region = regionRepository.findByCode(code);
        if (Objects.isNull(region)) {
            throw new ModelSaveException("Region not found");
        }
        regionRepository.delete(region);
    }


}
