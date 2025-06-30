package com.fiapi.facade.impl;

import com.fiapi.converter.DtoConverter;
import com.fiapi.dto.UserDetailsDTO;
import com.fiapi.exception.ModelSaveException;
import com.fiapi.facade.UserFacade;
import com.fiapi.model.UserModel;
import com.fiapi.service.I18NService;
import com.fiapi.service.UserService;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public class DefaultUserFacade implements UserFacade {

    private final I18NService i18NService;
    private final UserService userService;

    private final DtoConverter<UserModel, UserDetailsDTO> userDetailsDtoConverter;

    public DefaultUserFacade(I18NService i18NService, UserService userService, DtoConverter<UserModel, UserDetailsDTO> userDetailsDtoConverter) {
        this.i18NService = i18NService;
        this.userService = userService;
        this.userDetailsDtoConverter = userDetailsDtoConverter;
    }

    @Override
    public Optional<UserDetailsDTO> getUserDetails(@NotNull String userEmail) {
        Optional<UserModel> user = userService.getUserByEmail(userEmail);
        if (user.isPresent()) {
            return getUserDetails(user.get());
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public Optional<UserDetailsDTO> getUserDetails() throws RuntimeException {
        Optional<UserModel> currentUser = userService.getCurrentUser();
        if (currentUser.isPresent()) {
            return getUserDetails(currentUser.get());
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public Optional<UserDetailsDTO> getUserDetails(@NonNull UserModel user) {
        return Optional.of(userDetailsDtoConverter.convert(user));
    }

    @Override
    public void setCurrentLanguage(String languageCode) throws ModelSaveException {
        try {
            userService.getCurrentUser().ifPresentOrElse(user -> i18NService.fetchLanguageByCode(languageCode)
                    .ifPresentOrElse(language -> {
                        user.setCurrentLanguage(language);
                        saveUser(user);
                    }, () -> {
                        throw new RuntimeException("Language not found");
                    }), () -> {
                throw new RuntimeException("User not found");
            });
        } catch (RuntimeException e) {
            if (e.getCause() instanceof ModelSaveException cause) {
                throw cause;
            }
            throw new ModelSaveException(e.getMessage());
        }
    }

    void saveUser(@NotNull UserModel userModel) {
        try {
            userService.saveUser(userModel);
        } catch (ModelSaveException e) {
            throw new RuntimeException("Failed to save user language", e);
        }
    }

}
