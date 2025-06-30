package com.fiapi.facade;

import com.fiapi.dto.request.AuthenticationRequestDto;
import com.fiapi.dto.request.RegisterRequestDto;
import com.fiapi.dto.response.AuthenticationResponseDto;

import java.util.Optional;

public interface AuthenticationFacade {

    Optional<AuthenticationResponseDto> register(RegisterRequestDto request);

    Optional<AuthenticationResponseDto> authenticate(AuthenticationRequestDto request);

}
