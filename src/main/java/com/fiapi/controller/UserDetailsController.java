package com.fiapi.controller;

import com.fiapi.dto.UserDetailsDTO;
import com.fiapi.exception.ModelSaveException;
import com.fiapi.facade.UserFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserDetailsController {

    @Resource(name = "userFacade")
    private UserFacade userFacade;

    @PatchMapping("/setLanguage")
    @Operation(operationId = "setLanguage", summary = "Set user language")
    @ApiResponse(responseCode = "200", description = "Language set")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "404", description = "User or language not found")
    public ResponseEntity<Void> setCurrentLanguage(@RequestParam String languageCode) {
        try {
            userFacade.setCurrentLanguage(languageCode);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ModelSaveException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/current/getUserDetails")
    @Operation(operationId = "getUserDetails", summary = "Get user details")
    @ApiResponse(responseCode = "200", description = "User details found")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<UserDetailsDTO> getCurrentUserDetails() {
        return userFacade.getUserDetails()
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
