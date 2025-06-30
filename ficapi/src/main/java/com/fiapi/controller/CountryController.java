package com.fiapi.controller;

import com.fiapi.dto.CountryDto;
import com.fiapi.facade.CountryFacade;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/countries")
@Tag(name = "Countries", description = "Countries API")
public class CountryController {

    @Resource(name = "countryFacade")
    private CountryFacade countryFacade;

    @GetMapping("/getAll")
    @Operation(operationId = "getAllCountries", summary = "Get all countries")
    @ApiResponse(responseCode = "200", description = "Countries found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Iterable<CountryDto>> getAllCountries() {
        try {
            Iterable<CountryDto> countries = countryFacade.getAllCountries();
            return new ResponseEntity<>(countries, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    @Operation(operationId = "addCountry", summary = "Add a new country")
    @ApiResponse(responseCode = "201", description = "Country created")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Void> addCountry(@RequestBody CountryDto countryDto) {
        try {
            countryFacade.saveCountry(countryDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getByCode")
    @Operation(operationId = "getCountryByCode", summary = "Get country by code")
    @ApiResponse(responseCode = "200", description = "Country found")
    @ApiResponse(responseCode = "404", description = "Country not found")
    public ResponseEntity<CountryDto> getCountryByCode(@RequestParam String code) {
        return countryFacade.getCountryByCode(code)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getByName")
    @Operation(operationId = "getCountryByName", summary = "Get country by name")
    @ApiResponse(responseCode = "200", description = "Country found")
    @ApiResponse(responseCode = "404", description = "Country not found")
    public ResponseEntity<CountryDto> getCountryByName(@RequestParam String name) {
        return countryFacade.getCountryByName(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getCountryByIsoCode")
    @Operation(operationId = "getCountryByIsoCode", summary = "Get country by iso code")
    @ApiResponse(responseCode = "200", description = "Country found")
    @ApiResponse(responseCode = "404", description = "Country not found")
    public ResponseEntity<CountryDto> getCountryByIsoCode(@RequestParam String isoCode) {
        return countryFacade.getCountryByIsoCode(isoCode)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
}
