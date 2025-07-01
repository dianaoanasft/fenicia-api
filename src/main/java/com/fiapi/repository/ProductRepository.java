package com.fiapi.repository;

import com.fiapi.enums.StatusEnum;
import com.fiapi.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("productRepository")
public interface ProductRepository extends JpaRepository<ProductModel, Long> {

    @Query("SELECT p FROM ProductModel p WHERE p.code = :code")
    ProductModel findByCode(@Param("code") String code);

    @Query("SELECT p FROM ProductModel p")
    Iterable<ProductModel> findAllProducts();

    @Query("SELECT p FROM ProductModel p WHERE p.name LIKE %:name%")
    Iterable<ProductModel> findByNameContaining(@Param("name") String name);

    @Query("SELECT p FROM ProductModel p WHERE p.category = :category")
    Iterable<ProductModel> findByCategory(@Param("category") String category);

    @Query("SELECT p FROM ProductModel p WHERE p.brand = :brand")
    Iterable<ProductModel> findByBrand(@Param("brand") String brand);

    @Query("SELECT p FROM ProductModel p WHERE p.status = :status")
    Iterable<ProductModel> findByStatus(@Param("status") String status);

    @Query("SELECT p FROM ProductModel p WHERE p.country.code = :countryId")
    Iterable<ProductModel> findByCountryId(@Param("countryId") String countryId);

    @Query("SELECT p FROM ProductModel p WHERE p.code = :code AND p.status = :status")
    ProductModel findByCodeAndStatus(@Param("code") String code, @Param("status") StatusEnum status);

}
