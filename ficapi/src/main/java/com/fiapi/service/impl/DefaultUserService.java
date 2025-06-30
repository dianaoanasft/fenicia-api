package com.fiapi.service.impl;

import com.fiapi.exception.ModelSaveException;
import com.fiapi.model.UserModel;
import com.fiapi.repository.UserRepository;
import com.fiapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultUserService.class);

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserModel user = userRepository.findByEmail(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
        return user;
    }

    @Override
    public void saveUser(@NotNull UserModel userModel) throws ModelSaveException {
        if (Objects.isNull(userModel.getPk())) {
            logger.warn("User is new {}, generating new primary key", userModel.getEmail());
        }
        final UserModel savedUser = userRepository.save(userModel);
        if (Objects.isNull(savedUser.getPk())) {
            throw new ModelSaveException("Failed to save user");
        }
        logger.info("User saved successfully: {}", savedUser.getEmail());
    }

    @Override
    public Optional<UserModel> getUserByEmail(@NotNull String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Override
    public Optional<UserModel> getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (Objects.nonNull(authentication)
                && Objects.nonNull(authentication.getPrincipal())
                && authentication.getPrincipal() instanceof UserModel userModel) {
            return userRepository.findById(userModel.getPk());
        }
        return Optional.empty();
    }

}
