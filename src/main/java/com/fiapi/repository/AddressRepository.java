package com.fiapi.repository;

import com.fiapi.model.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("addressRepository")
public interface AddressRepository extends JpaRepository<AddressModel, Long> {

    // fetch address by pk if is default address equals = isDefaultAddress and user equals user with query
    @Query ("SELECT a FROM AddressModel a WHERE a.pk = :addressId AND a.isDeliveryAddress = true AND a.user.pk = :userId")
    AddressModel findDeliveryAddressByIdAndUser(@Param("addressId") Long pk, @Param("userId") Long userId);

}
