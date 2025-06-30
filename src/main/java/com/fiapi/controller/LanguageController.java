package com.fiapi.controller;

import com.fiapi.dto.LanguageDto;
import com.fiapi.exception.ModelSaveException;
import com.fiapi.facade.LanguageFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/languages")
@Tag(name = "Languages", description = "Languages API")
public class LanguageController {

    @Resource(name = "languageFacade")
    private LanguageFacade languageFacade;

    @PostMapping("/add")
    @Operation(operationId = "addLanguage", summary = "Add a new language")
    @ApiResponse(responseCode = "201", description = "Language created")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Void> addLanguage(@RequestBody LanguageDto languageDto) {
        try {
            languageFacade.saveLanguage(languageDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add/multiple")
    @Operation(operationId = "addMultipleLanguages", summary = "Add multiple languages")
    @ApiResponse(responseCode = "201", description = "Languages created")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Void> addMultipleLanguages(@RequestBody List<LanguageDto> languageDtos) {
        try {
            for (LanguageDto languageDto : languageDtos) {
                languageFacade.saveLanguage(languageDto);
            }
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (ModelSaveException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/update")
    @Operation(operationId = "updateLanguage", summary = "Update language")
    @ApiResponse(responseCode = "200", description = "Language updated")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Void> updateLanguage(@RequestBody LanguageDto languageDto) {
        try {
            languageFacade.updateLanguage(languageDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    @Operation(operationId = "deleteLanguage", summary = "Delete language")
    @ApiResponse(responseCode = "200", description = "Language deleted")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Void> deleteLanguage(@RequestParam String code) {
        try {
            languageFacade.deleteLanguage(code);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getByCode")
    @Operation(operationId = "getLanguageByCode", summary = "Get language by code")
    @ApiResponse(responseCode = "200", description = "Language found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "404", description = "Not found")
    public ResponseEntity<LanguageDto> getLanguageByCode(@RequestParam String code) {
        try {
            return languageFacade.getLanguageByCode(code)
                    .map(ResponseEntity::ok)
                    .orElseGet(ResponseEntity.notFound()::build);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getByName")
    @Operation(operationId = "getLanguageByName", summary = "Get language by name")
    @ApiResponse(responseCode = "200", description = "Language found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "404", description = "Not found")
    public ResponseEntity<LanguageDto> getLanguageByName(@RequestParam String name) {
        try {
            return languageFacade.getLanguageByName(name)
                    .map(ResponseEntity::ok)
                    .orElseGet(ResponseEntity.notFound()::build);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getByCountry")
    @Operation(operationId = "getLanguagesByCountry", summary = "Get languages by country")
    @ApiResponse(responseCode = "200", description = "Languages found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "404", description = "Not found")
    public ResponseEntity<List<LanguageDto>> getLanguagesByCountry(@RequestParam String country) {
        try {
            return ResponseEntity.ok(languageFacade.getLanguagesByCountry(country));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
