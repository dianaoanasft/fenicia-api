package com.fiapi.service;

import com.fiapi.model.CartModel;
import com.fiapi.model.OrderModel;

public interface OrderPlacementService {

    void saveOrder(OrderModel order);

    OrderModel placeOrder(CartModel cart);

}
