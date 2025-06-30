package com.fiapi.service;

import com.fiapi.model.OrderModel;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Optional<OrderModel> findByCode(String code);

    List<OrderModel> findAllByUserId(Long userId);
}
