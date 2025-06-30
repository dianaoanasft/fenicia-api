package com.fiapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentMethodDto extends ItemDto {

    private String paymentType;
    private String invoiceNumber;
    private String cardNumber;
    private String cardHolderName;
    private String cardExpiryDate;
    private String cardCVV;
    private String cardToken;

}
