package com.fiapi.facade;

import com.fiapi.dto.OrderDto;

import java.util.List;
import java.util.Optional;

public interface OrderFacade {

    Optional<OrderDto> getOrderDetails(String code);

    List<OrderDto> getOrderHistoryForCurrentUser();

}
