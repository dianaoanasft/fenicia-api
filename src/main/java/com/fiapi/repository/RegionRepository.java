package com.fiapi.repository;

import com.fiapi.model.CountryModel;
import com.fiapi.model.RegionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("regionRepository")
public interface RegionRepository extends JpaRepository<RegionModel, Long> {

    @Query("SELECT r FROM RegionModel r WHERE r.code = :code")
    RegionModel findByCode(@Param("code") String code);

    @Query("SELECT r FROM RegionModel r WHERE r.isoCode = :isoCode")
    RegionModel findByIsoCode(@Param("isoCode") String isoCode);

    @Query("SELECT r FROM RegionModel r WHERE r.name = :name")
    RegionModel findByName(@Param("name") String name);

    @Query("SELECT r FROM RegionModel r WHERE r.country = :country")
    List<RegionModel> findByCountry(@Param("country") CountryModel country);

}