package com.fiapi.facade;

import com.fiapi.dto.request.AuthenticationRequestDto;
import com.fiapi.dto.request.RegisterRequestDto;
import com.fiapi.dto.response.AuthenticationResponseDto;
import com.fiapi.enums.Role;
import com.fiapi.exception.ConversionException;
import com.fiapi.exception.ModelSaveException;
import com.fiapi.facade.impl.DefaultAuthenticationFacade;
import com.fiapi.model.UserModel;
import com.fiapi.service.JwtService;
import com.fiapi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DefaultAuthenticationFacadeTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private DefaultAuthenticationFacade authenticationFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldRegisterUserSuccessfully() throws ModelSaveException, ConversionException {
        RegisterRequestDto request = new RegisterRequestDto("John", "Doe", "john.doe@example.com", "password", "+40741468639");
        UserModel user = UserModel.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .role(Role.ADMIN)
                .password("encodedPassword")
                .build();

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        doNothing().when(userService).saveUser(user);

        Optional<AuthenticationResponseDto> response = authenticationFacade.register(request);

        assertThat(response).isPresent();
        assertThat(response.get().getMessage()).isEqualTo("User registered successfully");
        verify(userService, times(1)).saveUser(user);
    }

    @Test
    void shouldReturnEmptyOptional_whenRegisterFails() throws ModelSaveException, ConversionException {
        RegisterRequestDto request = new RegisterRequestDto("John", "Doe", "john.doe@example.com", "password", "+40741468639");

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        doThrow(new ModelSaveException("Failed to save user")).when(userService).saveUser(any(UserModel.class));

        Optional<AuthenticationResponseDto> response = authenticationFacade.register(request);

        assertThat(response).isEmpty();
        verify(userService, times(1)).saveUser(any(UserModel.class));
    }

    @Test
    void shouldAuthenticateUserSuccessfully() {
        AuthenticationRequestDto request = new AuthenticationRequestDto("john.doe@example.com", "password");
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("john.doe@example.com");
        when(userService.loadUserByUsername("john.doe@example.com")).thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn(Optional.of("jwtToken"));

        doAnswer(invocation -> null).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        Optional<AuthenticationResponseDto> response = authenticationFacade.authenticate(request);

        assertThat(response).isPresent();
        assertThat(response.get().getToken()).isEqualTo("jwtToken");
        assertThat(response.get().getMessage()).isEqualTo("User authenticated successfully");
    }

    @Test
    void shouldReturnEmptyOptional_whenAuthenticationFails() {
        AuthenticationRequestDto request = new AuthenticationRequestDto("john.doe@example.com", "password");

        doThrow(new RuntimeException("Authentication failed")).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        Optional<AuthenticationResponseDto> response = authenticationFacade.authenticate(request);

        assertThat(response).isEmpty();
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}
