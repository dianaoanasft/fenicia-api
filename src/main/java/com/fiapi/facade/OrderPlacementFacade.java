package com.fiapi.facade;

import com.fiapi.dto.OrderDto;

import java.util.Optional;

public interface OrderPlacementFacade {

    Optional<OrderDto> placeOrderOnCurrentCart();

}
