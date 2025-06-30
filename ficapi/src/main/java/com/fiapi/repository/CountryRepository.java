package com.fiapi.repository;

import com.fiapi.model.CountryModel;
import com.fiapi.model.RegionModel;
import com.fiapi.model.LanguageModel;
import com.fiapi.model.CurrencyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("countryRepository")
public interface CountryRepository extends JpaRepository<CountryModel, Long> {

    @Query("SELECT c FROM CountryModel c WHERE c.name = :name")
    CountryModel findByName(@Param("name") String name);

    @Query("SELECT c FROM CountryModel c WHERE c.code = :code")
    CountryModel findByCode(@Param("code") String code);

    @Query("SELECT c FROM CountryModel c WHERE c.isoCode = :isoCode")
    CountryModel findByIsoCode(@Param("isoCode") String isoCode);

    @Query("SELECT c FROM CountryModel c")
    List<CountryModel> findAll();

    @Query("SELECT c FROM CountryModel c JOIN c.regions r WHERE r = :region")
    List<CountryModel> findByRegion(@Param("region") RegionModel region);

    @Query("SELECT c FROM CountryModel c JOIN c.language l WHERE l = :language")
    List<CountryModel> findByLanguage(@Param("language") LanguageModel language);

    @Query("SELECT c FROM CountryModel c WHERE c.currency = :currency")
    List<CountryModel> findByCurrency(@Param("currency") CurrencyModel currency);
}