package com.fiapi.service.impl;

import com.fiapi.model.PaymentMethodModel;
import com.fiapi.repository.PaymentMethodRepository;
import com.fiapi.service.PaymentMethodService;

public class DefaultPaymentMethodService implements PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;

    public DefaultPaymentMethodService(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Override
    public void savePaymentMethod(PaymentMethodModel paymentMethod) {
        paymentMethodRepository.save(paymentMethod);
    }

}
