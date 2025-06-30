package com.fiapi.facade.impl;

import com.fiapi.converter.DtoConverter;
import com.fiapi.converter.ModelConverter;
import com.fiapi.dto.AddressDto;
import com.fiapi.exception.ModelSaveException;
import com.fiapi.facade.AddressFacade;
import com.fiapi.model.AddressModel;
import com.fiapi.model.UserModel;
import com.fiapi.service.AddressService;
import com.fiapi.service.UserService;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public class DefaultAddressFacade implements AddressFacade {

    private final AddressService addressService;
    private final UserService userService;

    private final ModelConverter<AddressDto, AddressModel> addressModelConverter;
    private final DtoConverter<AddressModel, AddressDto> addressDtoConverter;

    public DefaultAddressFacade(AddressService addressService, UserService userService, ModelConverter<AddressDto, AddressModel> addressModelConverter, DtoConverter<AddressModel, AddressDto> addressDtoConverter) {
        this.addressService = addressService;
        this.userService = userService;
        this.addressModelConverter = addressModelConverter;
        this.addressDtoConverter = addressDtoConverter;
    }

    @Override
    public void addAddress(@NonNull AddressDto addressDto) {
        userService.getCurrentUser().ifPresentOrElse(currentUser -> {
            final AddressModel address = addressModelConverter.convert(addressDto);
            address.setUser(currentUser);
            try {
                addressService.saveAddress(address);
            } catch (ModelSaveException e) {
                throw new RuntimeException("Failed to save address: " + e.getMessage(), e);
            }
        }, () -> {
            throw new RuntimeException("Current user not found");
        });
    }

    @Override
    public List<AddressDto> getAddressBookForCurrentUser() {
        Optional<UserModel> currentUserOptional = userService.getCurrentUser();
        if (!currentUserOptional.isPresent()) {
            throw new RuntimeException("Current user not found");
        }
        final UserModel currentUser = currentUserOptional.get();
        final List<AddressModel> addresses = currentUser.getAddresses();
        return addressDtoConverter.convertAll(addresses);
    }

}
