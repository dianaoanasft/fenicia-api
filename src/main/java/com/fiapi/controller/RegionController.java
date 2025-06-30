package com.fiapi.controller;

import com.fiapi.dto.RegionDto;
import com.fiapi.exception.ModelSaveException;
import com.fiapi.facade.RegionFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/regions")
@Tag(name = "Regions", description = "Regions API")
public class RegionController {

    @Resource(name = "regionFacade")
    private RegionFacade regionFacade;

    @PostMapping("/add")
    @Operation(operationId = "addRegion", summary = "Add a new region")
    @ApiResponse(responseCode = "201", description = "Region created")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Void> addRegion(@RequestBody RegionDto regionDto) {
        try {
            regionFacade.saveRegion(regionDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add/multiple")
    @Operation(operationId = "addMultipleRegions", summary = "Add multiple regions")
    @ApiResponse(responseCode = "201", description = "Regions created")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Void> addMultipleRegions(@RequestBody List<RegionDto> regionDtos) {
        try {
            for (RegionDto regionDto : regionDtos) {
                regionFacade.saveRegion(regionDto);
            }
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (ModelSaveException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    @Operation(operationId = "deleteRegion", summary = "Delete region")
    @ApiResponse(responseCode = "200", description = "Region deleted")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Void> deleteRegion(@RequestParam String code) {
        try {
            regionFacade.deleteRegion(code);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getByCode")
    @Operation(operationId = "getRegionByCode", summary = "Get region by code")
    @ApiResponse(responseCode = "200", description = "Region found")
    @ApiResponse(responseCode = "404", description = "Region not found")
    public ResponseEntity<RegionDto> getRegionByCode(@RequestParam String code) {
        return regionFacade.getRegionByCode(code)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/update")
    @Operation(operationId = "updateRegion", summary = "Update region")
    @ApiResponse(responseCode = "200", description = "Region updated")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Void> updateRegion(@RequestBody RegionDto regionDto) {
        try {
            regionFacade.updateRegion(regionDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getByName")
    @Operation(operationId = "getRegionByName", summary = "Get region by name")
    @ApiResponse(responseCode = "200", description = "Region found")
    @ApiResponse(responseCode = "404", description = "Region not found")
    public ResponseEntity<RegionDto> getRegionByName(@RequestParam String name) {
        return regionFacade.getRegionByName(name)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/getByIsoCode")
    @Operation(operationId = "getRegionByIsoCode", summary = "Get region by ISO code")
    @ApiResponse(responseCode = "200", description = "Region found")
    @ApiResponse(responseCode = "404", description = "Region not found")
    public ResponseEntity<RegionDto> getRegionByIsoCode(@RequestParam String isoCode) {
        return regionFacade.getRegionByIsoCode(isoCode)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/getByCountry")
    @Operation(operationId = "getRegionsByCountry", summary = "Get regions by country")
    @ApiResponse(responseCode = "200", description = "Regions found")
    @ApiResponse(responseCode = "404", description = "Regions not found")
    public ResponseEntity<List<RegionDto>> getRegionsByCountry(@RequestParam String country) {
        return ResponseEntity.ok(regionFacade.getRegionsByCountry(country));
    }

}
