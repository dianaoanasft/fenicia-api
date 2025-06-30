package com.fiapi.service;

import com.fiapi.exception.ModelSaveException;
import com.fiapi.model.UserModel;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    void saveUser(@NotNull UserModel userModel) throws ModelSaveException;

    Optional<UserModel> getUserByEmail(@javax.validation.constraints.NotNull String email);

    Optional<UserModel> getCurrentUser();
}
