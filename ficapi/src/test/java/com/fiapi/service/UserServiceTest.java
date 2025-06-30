package com.fiapi.service;

import com.fiapi.exception.ModelSaveException;
import com.fiapi.model.UserModel;
import com.fiapi.repository.UserRepository;
import com.fiapi.service.impl.DefaultUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DefaultUserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldThrowException_whenNoUserIsFound_forGivenUsername() {
        // given
        final String invalidUsername = "invalidUsername";
        when(userRepository.findByEmail(invalidUsername)).thenReturn(null);

        // then
        assertThatThrownBy(() -> userService.loadUserByUsername(invalidUsername))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("User not found with email: " + invalidUsername);
    }

    @Test
    void shouldReturnUserDetails_whenUserIsFound_forGivenUsername() {
        // given
        final String validUsername = "validUsername";
        final UserModel userModel = new UserModel();
        when(userRepository.findByEmail(validUsername)).thenReturn(userModel);

        // when
        final UserDetails userDetails = userService.loadUserByUsername(validUsername);

        // then
        assertThat(userDetails).isNotNull();
        assertThat(userDetails).isEqualTo(userModel);
    }


    @Test
    void shouldLogWarningAndSaveUser_whenUserIsNew() throws ModelSaveException {
        // given
        UserModel newUser = new UserModel();
        newUser.setEmail("testEmail");

        UserModel savedUser = new UserModel();
        savedUser.setEmail("testEmail");
        savedUser.setPk(1L);

        when(userRepository.save(newUser)).thenReturn(savedUser);

        // when
        userService.saveUser(newUser);

        // then
        assertThat(savedUser.getPk()).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo(newUser.getEmail());
    }

    @Test
    void shouldSaveUserSuccessfully() throws ModelSaveException {
        // given
        UserModel existingUser = new UserModel();
        existingUser.setPk(1L);
        existingUser.setEmail("existinguser@example.com");
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        // when
        userService.saveUser(existingUser);

        // then
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void shouldThrowModelSaveException_whenSavingUserFails() {
        // given
        UserModel user = new UserModel();
        user.setEmail("user@example.com");
        UserModel savedUser = new UserModel();
        when(userRepository.save(user)).thenReturn(savedUser);

        // when & then
        assertThatThrownBy(() -> userService.saveUser(user))
                .isInstanceOf(ModelSaveException.class)
                .hasMessageContaining("Failed to save user");
    }


}
