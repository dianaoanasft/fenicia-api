package com.fiapi.facade.impl;

import com.fiapi.dto.request.AuthenticationRequestDto;
import com.fiapi.dto.request.RegisterRequestDto;
import com.fiapi.dto.response.AuthenticationResponseDto;
import com.fiapi.enums.Role;
import com.fiapi.exception.ConversionException;
import com.fiapi.exception.ModelSaveException;
import com.fiapi.facade.AuthenticationFacade;
import com.fiapi.model.UserModel;
import com.fiapi.service.JwtService;
import com.fiapi.service.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@RequiredArgsConstructor
public class DefaultAuthenticationFacade implements AuthenticationFacade {

    private static final Logger logger = LoggerFactory.getLogger(DefaultAuthenticationFacade.class);

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public Optional<AuthenticationResponseDto> register(@NotNull RegisterRequestDto request) {
        try {
            final UserModel user = UserModel.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .role(Role.ADMIN)
                    .phoneNumber(request.getPhoneNumber())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .build();
            userService.saveUser(user);
            return Optional.of(AuthenticationResponseDto.builder()
                    .message("User registered successfully")
                    .build());
        } catch (ModelSaveException | ConversionException | DataIntegrityViolationException e) {
            logger.error("Error occurred while registering user: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<AuthenticationResponseDto> authenticate(@NotNull AuthenticationRequestDto request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            final UserDetails user = userService.loadUserByUsername(request.getEmail());
            final String token = jwtService.generateToken(user).get();
            return Optional.of(AuthenticationResponseDto.builder()
                    .token(token)
                    .message("User authenticated successfully")
                    .build());
        } catch (Exception e) {
            logger.error("Error occurred while authenticating user: {}", e.getMessage());
            return Optional.empty();
        }
    }

}
