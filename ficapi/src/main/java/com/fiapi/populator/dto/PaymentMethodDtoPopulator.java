package com.fiapi.populator.dto;

import com.fiapi.dto.PaymentMethodDto;
import com.fiapi.model.PaymentMethodModel;
import com.fiapi.populator.base.DtoPopulator;

public class PaymentMethodDtoPopulator implements DtoPopulator<PaymentMethodModel, PaymentMethodDto> {

    @Override
    public void populate(PaymentMethodModel source, PaymentMethodDto target) {
        target.setPaymentType(source.getPaymentType().name());
        target.setInvoiceNumber(source.getInvoiceNumber());
        target.setCardNumber(source.getCardNumber());
        target.setCardHolderName(source.getCardHolderName());
        target.setCardExpiryDate(source.getCardExpiryDate());
        target.setCardToken(source.getCardToken());
    }

}
