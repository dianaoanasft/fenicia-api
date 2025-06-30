package com.fiapi.facade;

import com.fiapi.dto.AddressDto;
import com.fiapi.exception.ModelSaveException;

import java.util.List;

public interface AddressFacade {

    void addAddress(AddressDto addressDto) throws ModelSaveException;

    List<AddressDto> getAddressBookForCurrentUser();
}
