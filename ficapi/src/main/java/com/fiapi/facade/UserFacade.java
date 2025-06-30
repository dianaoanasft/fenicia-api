package com.fiapi.facade;

import com.fiapi.dto.AddressDto;
import com.fiapi.dto.UserDetailsDTO;
import com.fiapi.exception.ModelSaveException;
import com.fiapi.model.UserModel;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface UserFacade {

    Optional<UserDetailsDTO> getUserDetails(@NotNull String userEmail);

    Optional<UserDetailsDTO> getUserDetails() throws RuntimeException;

    Optional<UserDetailsDTO> getUserDetails(@NonNull UserModel user);

    void setCurrentLanguage(String languageCode) throws ModelSaveException;

}
