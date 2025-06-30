package com.fiapi.controller;

import com.fiapi.dto.CurrencyDto;
import com.fiapi.exception.ModelSaveException;
import com.fiapi.facade.CurrencyFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/currencies")
@Tag(name = "Currencies", description = "Currencies API")
public class CurrencyController {

    @Resource(name = "currencyFacade")
    private CurrencyFacade currencyFacade;

    @PostMapping("/add")
    @Operation(operationId = "addCurrency", summary = "Add a new currency")
    @ApiResponse(responseCode = "201", description = "Currency created")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Void> addCurrency(@RequestBody CurrencyDto currencyDto) {
        try {
            currencyFacade.saveCurrency(currencyDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add/multiple")
    @Operation(operationId = "addMultipleCurrencies", summary = "Add multiple currencies")
    @ApiResponse(responseCode = "201", description = "Currencies created")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Void> addMultipleCurrencies(@RequestBody List<CurrencyDto> currencyDtos) {
        try {
            for (CurrencyDto currencyDto : currencyDtos) {
                currencyFacade.saveCurrency(currencyDto);
            }
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (ModelSaveException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getByCode")
    @Operation(operationId = "getCurrencyByCode", summary = "Get currency by code")
    @ApiResponse(responseCode = "200", description = "Currency found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "404", description = "Currency not found")
    public ResponseEntity<CurrencyDto> getCurrencyByCode(@RequestParam String code) {
        try {
            return currencyFacade.getCurrencyByCode(code)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getByName")
    @Operation(operationId = "getCurrencyByName", summary = "Get currency by name")
    @ApiResponse(responseCode = "200", description = "Currency found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "404", description = "Currency not found")
    public ResponseEntity<CurrencyDto> getCurrencyByName(@RequestParam String name) {
        try {
            return currencyFacade.getCurrencyByName(name)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getByCountry")
    @Operation(operationId = "getCurrencyByCountry", summary = "Get currency by country")
    @ApiResponse(responseCode = "200", description = "Currency found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "404", description = "Currency not found")
    public ResponseEntity<List<CurrencyDto>> getCurrencyByCountry(@RequestParam String country) {
        try {
            return ResponseEntity.ok(currencyFacade.getCurrenciesByCountry(country));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/update")
    @Operation(operationId = "updateCurrency", summary = "Update currency")
    @ApiResponse(responseCode = "200", description = "Currency updated")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Void> updateCurrency(@RequestBody CurrencyDto currencyDto) {
        try {
            currencyFacade.updateCurrency(currencyDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    @Operation(operationId = "deleteCurrency", summary = "Delete currency")
    @ApiResponse(responseCode = "200", description = "Currency deleted")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Void> deleteCurrency(@RequestParam String code) {
        try {
            currencyFacade.deleteCurrency(code);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
