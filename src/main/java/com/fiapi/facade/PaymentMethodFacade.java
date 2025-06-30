package com.fiapi.facade;

import com.fiapi.dto.PaymentMethodDto;
import com.fiapi.model.PaymentMethodModel;

public interface PaymentMethodFacade {

    PaymentMethodModel tokenizePaymentMethod(PaymentMethodDto paymentMethodDto);

    boolean isCreditCardPaymentMethodValid(PaymentMethodDto paymentMethodDto);

}
