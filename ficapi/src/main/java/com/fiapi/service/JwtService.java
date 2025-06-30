package com.fiapi.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.Optional;

public interface JwtService {

    Optional<String> extractUsername(String jwtToken);

    Optional<String> generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

    Optional<String> generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

    boolean isTokenExpired(String token);

}
