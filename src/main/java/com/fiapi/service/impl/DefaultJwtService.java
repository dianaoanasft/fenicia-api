package com.fiapi.service.impl;

import com.fiapi.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class DefaultJwtService implements JwtService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultJwtService.class);

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Override
    public Optional<String> extractUsername(String jwtToken) {
        final String claim = extractClaim(jwtToken, Claims::getSubject);
        return Optional.ofNullable(claim);
    }

    @Override
    public Optional<String> generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        try {
            final String token = Jwts
                    .builder()
                    .setClaims(extraClaims)
                    .setSubject(userDetails.getUsername())
                    .setIssuedAt(new java.util.Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS512)
                    .compact();
            return Optional.of(token);
        } catch (Exception e) {
            logger.error("Error generating JWT token", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token).orElse(null);
        return StringUtils.isNotBlank(username)
                && username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        try {
            final Claims claims = extractAllClaims(jwtToken);
            return claimsResolver.apply(claims);
        } catch (JwtException e) {
            logger.error("Error extracting claim from JWT token", e);
            return null;
        }
    }

    private Claims extractAllClaims(@NotBlank String jwtToken) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody();
        } catch (Exception e) {
            throw new JwtException("Invalid JWT token");
        }
    }

    private Key getSignInKey() {
        final byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}