package com.fiapi.facade;

import com.fiapi.dto.CountryDto;
import com.fiapi.exception.ModelSaveException;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface CountryFacade {

    List<CountryDto> getAllCountries();

    Optional<CountryDto> getCountryByCode(@NotBlank String code);

    Optional<CountryDto> getCountryByIsoCode(@NotBlank String isoCode);

    Optional<CountryDto> getCountryByName(@NotBlank String name);

    void saveCountry(@NotNull CountryDto countryDto) throws ModelSaveException;

}
