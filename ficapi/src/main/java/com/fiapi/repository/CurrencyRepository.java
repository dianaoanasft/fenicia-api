package com.fiapi.repository;

import com.fiapi.model.CountryModel;
import com.fiapi.model.CurrencyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("currencyRepository")
public interface CurrencyRepository extends JpaRepository<CurrencyModel, Long> {

    @Query("SELECT c FROM CurrencyModel c WHERE c.code = :code")
    CurrencyModel findByCode(@Param("code") String code);

    @Query("SELECT c FROM CurrencyModel c WHERE c.name = :name")
    CurrencyModel findByName(@Param("name") String name);

    @Query("SELECT c FROM CurrencyModel c JOIN c.countries co WHERE co = :country")
    List<CurrencyModel> findByCountry(@Param("country") CountryModel country);
}