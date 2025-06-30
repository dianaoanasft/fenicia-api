package com.fiapi.controller;

import com.fiapi.dto.request.AuthenticationRequestDto;
import com.fiapi.dto.request.RegisterRequestDto;
import com.fiapi.dto.response.AuthenticationResponseDto;
import com.fiapi.facade.AuthenticationFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Authentication API")
@SecurityRequirement(name = "bearerAuth")
public class AuthenticationController {

    @Resource(name = "authenticationFacade")
    private AuthenticationFacade authenticationFacade;

    @PostMapping("/signup")
    @Operation(operationId = "register", summary = "Register a new user")
    @ApiResponse(responseCode = "200", description = "User registered")
    @ApiResponse(responseCode = "400", description = "Bad request")
    public ResponseEntity<AuthenticationResponseDto> register(@RequestBody RegisterRequestDto request) {
        return authenticationFacade.register(request)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body(
                        AuthenticationResponseDto.builder()
                                .message("Registration failed")
                                .build()
                ));
    }

    @PostMapping("/signin")
    @Operation(operationId = "authenticate", summary = "Authenticate user")
    @ApiResponse(responseCode = "200", description = "User authenticated")
    @ApiResponse(responseCode = "400", description = "Bad request")
    public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody AuthenticationRequestDto request) {
        return authenticationFacade.authenticate(request)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body(
                        AuthenticationResponseDto.builder()
                                .message("Authenticate failed")
                                .build()
                ));
    }

}
