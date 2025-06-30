package com.fiapi.repository;

import com.fiapi.model.CountryModel;
import com.fiapi.model.LanguageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("languageRepository")
public interface LanguageRepository extends JpaRepository<LanguageModel, Long> {

    @Query("SELECT l FROM LanguageModel l WHERE l.code = :code")
    LanguageModel findByCode(@Param("code") String code);

    @Query("SELECT l FROM LanguageModel l WHERE l.name = :name")
    LanguageModel findByName(@Param("name") String name);

    @Query("SELECT l FROM LanguageModel l JOIN l.countries c WHERE c = :country")
    List<LanguageModel> findByCountry(@Param("country") CountryModel country);
}