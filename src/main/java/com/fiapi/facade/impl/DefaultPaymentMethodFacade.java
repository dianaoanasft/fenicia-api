package com.fiapi.facade.impl;

import com.fiapi.dto.PaymentMethodDto;
import com.fiapi.enums.PaymentType;
import com.fiapi.facade.PaymentMethodFacade;
import com.fiapi.model.PaymentMethodModel;
import com.fiapi.service.PaymentMethodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DefaultPaymentMethodFacade implements PaymentMethodFacade {

    private static final Logger logger = LoggerFactory.getLogger(DefaultPaymentMethodFacade.class);

    private static final String INVALID = "The given request body is valid.";

    private final PaymentMethodService paymentMethodService;

    public DefaultPaymentMethodFacade(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @Override
    public PaymentMethodModel tokenizePaymentMethod(@NonNull PaymentMethodDto paymentMethodDto) {
        final PaymentMethodModel paymentMethodModel = new PaymentMethodModel();

        if (PaymentType.INVOICE.name().equals(paymentMethodDto.getPaymentType())) {
            paymentMethodModel.setPaymentType(PaymentType.INVOICE);
            paymentMethodService.savePaymentMethod(paymentMethodModel);
            return paymentMethodModel;
        } else if (PaymentType.CREDIT_CARD.name().equals(paymentMethodDto.getPaymentType())) {
            paymentMethodModel.setPaymentType(PaymentType.CREDIT_CARD);
            paymentMethodModel.setCardNumber("**** **** **** " + paymentMethodDto.getCardNumber().substring(paymentMethodDto.getCardNumber().length() - 3));
            paymentMethodModel.setCardHolderName(paymentMethodDto.getCardHolderName());
            paymentMethodModel.setCardExpiryDate(paymentMethodDto.getCardExpiryDate());
            paymentMethodModel.setCardToken(encryptCardDetails(paymentMethodDto.getCardNumber(), paymentMethodDto.getCardCVV()));

            paymentMethodService.savePaymentMethod(paymentMethodModel);
            return paymentMethodModel;
        }
        return null;
    }

    @Override
    public boolean isCreditCardPaymentMethodValid(@NonNull PaymentMethodDto paymentMethodDto) {
        if (PaymentType.CREDIT_CARD.name().equals(paymentMethodDto.getPaymentType())) {
            if (paymentMethodDto.getCardNumber() == null
                    || paymentMethodDto.getCardHolderName() == null
                    || paymentMethodDto.getCardExpiryDate() == null
                    || paymentMethodDto.getCardCVV() == null) {
                return false;
            }
            if (!paymentMethodDto.getCardNumber().matches("\\d{16}")) {
                return false;
            }
            if (!paymentMethodDto.getCardExpiryDate().matches("(0[1-9]|1[0-2])/[0-9]{2}")) {
                return false;
            }
            String[] parts = paymentMethodDto.getCardExpiryDate().split("/");
            int month = Integer.parseInt(parts[0]);
            int year = Integer.parseInt(parts[1]) + 2000;
            java.time.LocalDate expiryDate = java.time.LocalDate.of(year, month, 1);
            java.time.LocalDate currentDate = java.time.LocalDate.now();
            boolean valid = !expiryDate.isBefore(currentDate);
            if (valid) {
                logger.error(INVALID);
            }
            return valid;
        } else if (PaymentType.INVOICE.name().equals(paymentMethodDto.getPaymentType())) {
            return paymentMethodDto.getCardNumber() == null && paymentMethodDto.getCardHolderName() == null
                    && paymentMethodDto.getCardExpiryDate() == null && paymentMethodDto.getCardCVV() == null;
        }
        return false;
    }

    private String encryptCardDetails(@NonNull String cardNumber, @NonNull String cardCvv) {
        final String initialValue = cardNumber + cardCvv;
        return Base64.getEncoder().encodeToString(initialValue.getBytes(StandardCharsets.UTF_8));
    }

}
