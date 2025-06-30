package com.fiapi.service;

import com.fiapi.exception.ModelSaveException;
import com.fiapi.model.AddressModel;
import com.fiapi.model.UserModel;

import java.util.Optional;

public interface AddressService {

    void saveAddress(AddressModel address) throws ModelSaveException;

    Optional<AddressModel> fetchDeliveryAddressByIdAndUser(Long addressId, UserModel userId);

}
