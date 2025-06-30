package com.fiapi.service.impl;

import com.fiapi.model.OrderModel;
import com.fiapi.repository.OrderRepository;
import com.fiapi.service.OrderService;

import java.util.List;
import java.util.Optional;

public class DefaultOrderService implements OrderService {

    private final OrderRepository orderRepository;

    public DefaultOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Optional<OrderModel> findByCode(String code) {
        return Optional.ofNullable(orderRepository.findByCode(code));
    }

    @Override
    public List<OrderModel> findAllByUserId(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }
}
