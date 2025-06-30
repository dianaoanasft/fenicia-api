package com.fiapi.facade.impl;

import com.fiapi.converter.DtoConverter;
import com.fiapi.dto.OrderDto;
import com.fiapi.facade.OrderPlacementFacade;
import com.fiapi.model.OrderModel;
import com.fiapi.model.UserModel;
import com.fiapi.service.OrderPlacementService;
import com.fiapi.service.UserService;

import java.util.Optional;

public class DefaultOrderPlacementFacade implements OrderPlacementFacade {

    private final UserService userService;
    private final OrderPlacementService orderPlacementService;

    private final DtoConverter<OrderModel, OrderDto> orderDtoConverter;

    public DefaultOrderPlacementFacade(UserService userService, OrderPlacementService orderPlacementService,
                                       DtoConverter<OrderModel, OrderDto> orderDtoConverter) {
        this.userService = userService;
        this.orderPlacementService = orderPlacementService;
        this.orderDtoConverter = orderDtoConverter;
    }

    @Override
    public Optional<OrderDto> placeOrderOnCurrentCart() {
        final Optional<UserModel> currentUserOptional = userService.getCurrentUser();
        if (currentUserOptional.isPresent()) {
            final UserModel currentUser = currentUserOptional.get();
            final OrderModel orderDto = orderPlacementService.placeOrder(currentUser.getCart());
            return Optional.of(orderDtoConverter.convert(orderDto));
        }
        return Optional.empty();
    }

}
