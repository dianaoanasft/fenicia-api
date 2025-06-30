package com.fiapi.service;

import com.fiapi.model.CartModel;
import org.springframework.lang.NonNull;

public interface OrderSimulateService {

    void performOrderSimulate(@NonNull CartModel cart);

}
