package com.fiapi.controller;

import com.fiapi.dto.CartDto;
import com.fiapi.facade.CartFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@Tag(name = "Cart", description = "Cart API")
public class CartController {

    @Resource(name = "cartFacade")
    private CartFacade cartFacade;

    @PostMapping("/current/create")
    @Operation(operationId = "createCart", summary = "Create a new cart for the current user")
    @ApiResponse(responseCode = "201", description = "Cart created successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<String> createCart() {
        try {
            cartFacade.createCartForCurrentUser();
            return ResponseEntity
                    .status(201)
                    .body("Cart created successfully");
        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body("Failed to create cart.");
        }
    }

    @DeleteMapping("/current/delete")
    @Operation(operationId = "deleteCart", summary = "Delete the cart for the current user")
    @ApiResponse(responseCode = "204", description = "Cart deleted successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<String> deleteCart() {
        try {
            cartFacade.deleteCartForCurrentUser();
            return ResponseEntity
                    .status(204)
                    .body("Cart deleted successfully");
        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body("Failed to delete cart.");
        }
    }

    @GetMapping("/current/cart")
    @Operation(operationId = "getCartDetails", summary = "Get cart details for the current user")
    @ApiResponse(responseCode = "200", description = "Cart details retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Cart not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<CartDto> getCartDetails() {
        try {
            return cartFacade.getCartDetailsForCurrentUser()
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body(null);
        }
    }

    @PostMapping("/current/cart/addItem")
    @Operation(operationId = "addItemToCart", summary = "Add an item to the cart for the current user")
    @ApiResponse(responseCode = "200", description = "Item added to cart successfully")
    @ApiResponse(responseCode = "404", description = "Cart not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<CartDto> addItemToCart(@RequestParam String productCode, @RequestParam int quantity) {
        try {
            return cartFacade.addItemToCartForCurrentUser(productCode, quantity)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body(null);
        }
    }

    @PatchMapping("/current/cart/updateItem")
    @Operation(operationId = "updateItemQuantityInCart", summary = "Update item quantity in the cart for the current user")
    @ApiResponse(responseCode = "200", description = "Item quantity updated successfully")
    @ApiResponse(responseCode = "404", description = "Cart or item not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<CartDto> updateItemQuantityInCart(@RequestParam String productCode, @RequestParam int quantity) {
        try {
            return cartFacade.updateItemQuantityInCartForCurrentUser(productCode, quantity)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body(null);
        }
    }


}
