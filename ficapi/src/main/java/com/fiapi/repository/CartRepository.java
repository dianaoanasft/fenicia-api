package com.fiapi.repository;

import com.fiapi.model.CartModel;
import com.fiapi.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("cartRepository")
public interface CartRepository extends JpaRepository<CartModel, Long> {

    @Query("SELECT c FROM CartModel c WHERE c.owner = :user")
    CartModel findByOwner(UserModel user);

    @Query("SELECT c FROM CartModel c WHERE c.code = :code")
    CartModel findByCode(String code);

}
