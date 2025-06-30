package com.fiapi.controller;

import com.fiapi.dto.CartDto;
import com.fiapi.dto.OrderDto;
import com.fiapi.dto.PaymentMethodDto;
import com.fiapi.facade.CartFacade;
import com.fiapi.facade.OrderPlacementFacade;
import com.fiapi.facade.PaymentMethodFacade;
import com.fiapi.model.PaymentMethodModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/checkout")
@Tag(name = "Checkout", description = "Checkout API")
public class CheckoutController {

    @Resource(name = "cartFacade")
    private CartFacade cartFacade;

    @Resource(name = "paymentMethodFacade")
    private PaymentMethodFacade paymentMethodFacade;

    @Resource(name = "orderPlacementFacade")
    private OrderPlacementFacade orderPlacementFacade;

    @PostMapping("/cart/current/setDeliveryAddress")
    @Operation(operationId = "setDeliveryAddress", summary = "Set delivery address for current user's cart")
    @ApiResponse(responseCode = "200", description = "Delivery address set successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<CartDto> setDeliveryAddress(@RequestParam("addressId") Long addressId) {
        return cartFacade.setDeliveryAddressOnCurrentCart(addressId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(500).build());
    }

    @PostMapping("/cart/current/setPaymentMethod")
    @Operation(operationId = "setPaymentMethod", summary = "Set payment method for current user's cart")
    @ApiResponse(responseCode = "200", description = "Payment method set successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<CartDto> setPaymentMethod(@RequestBody PaymentMethodDto paymentMethodDto) {
        if (paymentMethodFacade.isCreditCardPaymentMethodValid(paymentMethodDto)) {
            final PaymentMethodModel tokenizedPaymentMethod = paymentMethodFacade.tokenizePaymentMethod(paymentMethodDto);
            if (Objects.nonNull(tokenizedPaymentMethod)) {
                return cartFacade.setPaymentMethodOnCurrentCart(tokenizedPaymentMethod)
                        .map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.status(500).build());
            }
            return ResponseEntity.status(500).build();
        }
        return ResponseEntity.status(500).build();
    }

    @PostMapping("/cart/current/placeOrder")
    @Operation(operationId = "placeOrder", summary = "Place order for current user's cart")
    @ApiResponse(responseCode = "200", description = "Order placed successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<OrderDto> placeOrder() {
        return orderPlacementFacade.placeOrderOnCurrentCart()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(500).build());
    }


}
