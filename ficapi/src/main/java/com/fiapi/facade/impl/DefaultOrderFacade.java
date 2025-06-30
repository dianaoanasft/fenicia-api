package com.fiapi.facade.impl;

import com.fiapi.converter.DtoConverter;
import com.fiapi.dto.OrderDto;
import com.fiapi.facade.OrderFacade;
import com.fiapi.model.OrderModel;
import com.fiapi.model.UserModel;
import com.fiapi.service.OrderService;
import com.fiapi.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DefaultOrderFacade implements OrderFacade {

    private final OrderService orderService;
    private final UserService userService;

    private final DtoConverter<OrderModel, OrderDto> orderDtoConverter;

    public DefaultOrderFacade(OrderService orderService, UserService userService,
                              DtoConverter<OrderModel, OrderDto> orderDtoConverter) {
        this.orderService = orderService;
        this.userService = userService;
        this.orderDtoConverter = orderDtoConverter;
    }

    @Override
    public Optional<OrderDto> getOrderDetails(String code) {
        return orderService.findByCode(code)
                .map(orderDtoConverter::convert);
    }

    @Override
    public List<OrderDto> getOrderHistoryForCurrentUser() {
        Optional<UserModel> currentUser = userService.getCurrentUser();
        if (currentUser.isPresent()) {
            List<OrderModel> orders = orderService.findAllByUserId(currentUser.get().getPk());
            return orderDtoConverter.convertAll(orders);
        }
        return Collections.emptyList();
    }
}
