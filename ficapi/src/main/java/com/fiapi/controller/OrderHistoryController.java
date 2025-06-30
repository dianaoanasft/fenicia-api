package com.fiapi.controller;

import com.fiapi.dto.OrderDto;
import com.fiapi.facade.OrderFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-history")
@Tag(name = "Order History", description = "Order History API")
public class OrderHistoryController {

    @Resource(name = "orderFacade")
    private OrderFacade orderFacade;

    @GetMapping
    @Operation(operationId = "getOrderHistory", summary = "Get order history for the current user")
    @ApiResponse(responseCode = "200", description = "Order history retrieved successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<OrderDto>> getOrderHistory() {
        List<OrderDto> orderHistory = orderFacade.getOrderHistoryForCurrentUser();
        if (CollectionUtils.isNotEmpty(orderHistory)) {
            return ResponseEntity.ok(orderHistory);
        } else {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/orderDetails")
    @Operation(operationId = "getOrderDetails", summary = "Get details of a specific order")
    @ApiResponse(responseCode = "200", description = "Order details retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Order not found")
    public ResponseEntity<OrderDto> getOrderDetails(@RequestParam("orderId") String orderId) {
        return orderFacade.getOrderDetails(orderId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).build());
    }

}
