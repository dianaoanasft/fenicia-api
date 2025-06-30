package com.fiapi.facade;

import com.fiapi.converter.DtoConverter;
import com.fiapi.dto.UserDetailsDTO;
import com.fiapi.exception.ModelSaveException;
import com.fiapi.facade.impl.DefaultUserFacade;
import com.fiapi.model.LanguageModel;
import com.fiapi.model.UserModel;
import com.fiapi.service.I18NService;
import com.fiapi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DefaultUserFacadeTest {

    @Mock
    private I18NService i18NService;

    @Mock
    private UserService userService;

    @Mock
    private DtoConverter<UserModel, UserDetailsDTO> userDetailsDtoConverter;

    @InjectMocks
    private DefaultUserFacade defaultUserFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserDetailsByEmail() {
        String email = "test@example.com";
        UserModel userModel = new UserModel();
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();

        when(userService.getUserByEmail(email)).thenReturn(Optional.of(userModel));
        when(userDetailsDtoConverter.convert(userModel)).thenReturn(userDetailsDTO);

        Optional<UserDetailsDTO> result = defaultUserFacade.getUserDetails(email);

        assertTrue(result.isPresent());
        assertEquals(userDetailsDTO, result.get());
    }

    @Test
    void testGetUserDetailsByEmail_UserNotFound() {
        String email = "test@example.com";

        when(userService.getUserByEmail(email)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> defaultUserFacade.getUserDetails(email));
    }

    @Test
    void testGetCurrentUserDetails() {
        UserModel userModel = new UserModel();
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();

        when(userService.getCurrentUser()).thenReturn(Optional.of(userModel));
        when(userDetailsDtoConverter.convert(userModel)).thenReturn(userDetailsDTO);

        Optional<UserDetailsDTO> result = defaultUserFacade.getUserDetails();

        assertTrue(result.isPresent());
        assertEquals(userDetailsDTO, result.get());
    }

    @Test
    void testGetCurrentUserDetails_UserNotFound() {
        when(userService.getCurrentUser()).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> defaultUserFacade.getUserDetails());
    }

    @Test
    void testSetCurrentLanguage() throws ModelSaveException {
        String languageCode = "en";
        UserModel userModel = new UserModel();

        when(userService.getCurrentUser()).thenReturn(Optional.of(userModel));
        when(i18NService.fetchLanguageByCode(languageCode)).thenReturn(Optional.of(new LanguageModel()));

        defaultUserFacade.setCurrentLanguage(languageCode);

        verify(userService, times(1)).saveUser(userModel);
    }

}