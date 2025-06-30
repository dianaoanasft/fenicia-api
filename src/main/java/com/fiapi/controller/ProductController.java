package com.fiapi.controller;

import com.fiapi.dto.ProductDto;
import com.fiapi.facade.ProductFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Products", description = "Products API")
public class ProductController {

    @Resource(name = "productFacade")
    private ProductFacade productFacade;

    @PostMapping("/add")
    @Operation(operationId = "addProduct", summary = "Add a new product")
    @ApiResponse(responseCode = "200", description = "Product added successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "400", description = "Bad request")
    public ResponseEntity<String> addProduct(@RequestBody ProductDto product) {
        try {
            productFacade.addProduct(product);
            return ResponseEntity.ok("Product added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }

    @PostMapping("/update")
    @Operation(operationId = "updateProduct", summary = "Update an existing product")
    @ApiResponse(responseCode = "200", description = "Product updated successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "400", description = "Bad request")
    public ResponseEntity<String> updateProduct(@RequestBody ProductDto product) {
        try {
            productFacade.updateProduct(product);
            return ResponseEntity.ok("Product updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }

    @PostMapping("/delete")
    @Operation(operationId = "deleteProduct", summary = "Delete a product by SKU")
    @ApiResponse(responseCode = "200", description = "Product deleted successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "400", description = "Bad request")
    public ResponseEntity<String> deleteProduct(@RequestParam String productSku) {
        try {
            productFacade.deleteProduct(productSku);
            return ResponseEntity.ok("Product deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
        }
    }

    @GetMapping("/get")
    @Operation(operationId = "getProduct", summary = "Get a product by SKU")
    @ApiResponse(responseCode = "200", description = "Product retrieved successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "400", description = "Bad request")
    public ResponseEntity<ProductDto> getProduct(@RequestParam String productSku) {
        try {
            Optional<ProductDto> product = productFacade.findProductByCode(productSku);
            return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).body(null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/getAll")
    @Operation(operationId = "getAllProducts", summary = "Get all products")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "400", description = "Bad request")
    public ResponseEntity<Iterable<ProductDto>> getAllProducts() {
        try {
            Iterable<ProductDto> products = productFacade.findAllProducts();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/getByCategory")
    @Operation(operationId = "getProductsByCategory", summary = "Get products by category")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "400", description = "Bad request")
    public ResponseEntity<Iterable<ProductDto>> getProductsByCategory(@RequestParam String category) {
        try {
            Iterable<ProductDto> products = productFacade.findProductsByCategory(category);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/getByBrand")
    @Operation(operationId = "getProductsByBrand", summary = "Get products by brand")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "400", description = "Bad request")
    public ResponseEntity<Iterable<ProductDto>> getProductsByBrand(@RequestParam String brand) {
        try {
            Iterable<ProductDto> products = productFacade.findProductsByBrand(brand);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/get/{productSku}/{status}")
    @Operation(operationId = "getProductBySkuAndStatus", summary = "Get a product by SKU and status")
    @ApiResponse(responseCode = "200", description = "Product retrieved successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "400", description = "Bad request")
    public ResponseEntity<ProductDto> getProductBySkuAndStatus(@PathVariable String productSku, @PathVariable String status) {
        try {
            Optional<ProductDto> product = productFacade.findProductByCodeAndStatus(productSku, status);
            return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).body(null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/getByName")
    @Operation(operationId = "getProductsByName", summary = "Get products by name")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "400", description = "Bad request")
    public ResponseEntity<Iterable<ProductDto>> getProductsByName(@RequestParam String name) {
        try {
            Iterable<ProductDto> products = productFacade.findProductsByName(name);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/geyByStatus")
    @Operation(operationId = "getProductsByStatus", summary = "Get products by status")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "400", description = "Bad request")
    public ResponseEntity<Iterable<ProductDto>> getProductsByStatus(@RequestParam String status) {
        try {
            Iterable<ProductDto> products = productFacade.findProductsByStatus(status);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/getByCountry")
    @Operation(operationId = "getProductsByCountry", summary = "Get products by country")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @ApiResponse(responseCode = "400", description = "Bad request")
    public ResponseEntity<Iterable<ProductDto>> getProductsByCountry(@RequestParam String country) {
        try {
            Iterable<ProductDto> products = productFacade.findProductsByCountryId(country);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

}