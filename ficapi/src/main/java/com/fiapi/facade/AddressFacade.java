package com.fiapi.facade;

import com.fiapi.dto.AddressDto;
import com.fiapi.exception.ConversionException;
import com.fiapi.exception.ModelSaveException;
import com.fiapi.model.UserModel;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface AddressFacade {

    void addAddress(AddressDto addressDto) throws ModelSaveException;

    List<AddressDto> getAddressBookForCurrentUser();
}
