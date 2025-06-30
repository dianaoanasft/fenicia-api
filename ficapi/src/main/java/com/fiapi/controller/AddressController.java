package com.fiapi.controller;

import com.fiapi.dto.AddressDto;
import com.fiapi.facade.AddressFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/addresses")
@Tag(name = "Addresses", description = "Addresses API")
public class AddressController {

    @Resource(name = "addressFacade")
    private AddressFacade addressFacade;

    @PostMapping("/current/add")
    @Operation(operationId = "addAddress", summary = "Add a new address for current user")
    @ApiResponse(responseCode = "201", description = "Address created")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<String> addAddress(@RequestBody AddressDto addressDto) {
        try {
            addressFacade.addAddress(addressDto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Address created successfully");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create address.");
        }
    }

    @GetMapping("/current/getAll")
    @Operation(operationId = "getAllAddresses", summary = "Get all addresses for current user")
    @ApiResponse(responseCode = "200", description = "Addresses retrieved successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Iterable<AddressDto>> getAddressBook() {
        try {
            Iterable<AddressDto> addresses = addressFacade.getAddressBookForCurrentUser();
            return ResponseEntity.ok(addresses);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

}
