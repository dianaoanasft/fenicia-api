package com.fiapi.service.impl;

import com.fiapi.exception.ModelSaveException;
import com.fiapi.model.AddressModel;
import com.fiapi.model.UserModel;
import com.fiapi.repository.AddressRepository;
import com.fiapi.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.util.Objects;
import java.util.Optional;

public class DefaultAddressService implements AddressService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultAddressService.class);

    private final AddressRepository addressRepository;

    public DefaultAddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public void saveAddress(AddressModel address) throws ModelSaveException {
        if (Objects.isNull(address.getPk())) {
            logger.warn("Country is new {}, generating new primary key", address.getAlias());
        }
        final AddressModel savedAddress = addressRepository.save(address);
        if (Objects.isNull(savedAddress.getPk())) {
            throw new ModelSaveException("Failed to save country");
        }
        logger.info("Country saved successfully: {}", savedAddress.getPk());
    }

    @Override
    public Optional<AddressModel> fetchDeliveryAddressByIdAndUser(@NonNull Long addressId, @NonNull UserModel user) {
        return Optional.ofNullable(addressRepository.findDeliveryAddressByIdAndUser(addressId, user.getPk()));
    }

}
