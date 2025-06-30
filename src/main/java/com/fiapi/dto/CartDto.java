package com.fiapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartDto extends ItemDto{

    private String code;
    private String owner;
    private Double basePrice;
    private Double discountPrice;
    private Double taxPrice;
    private Double totalPrice;

    private String creationTime;
    private String modifiedTime;

    private AddressDto deliveryAddress;
    private PaymentMethodDto paymentMethod;

    private List<CartEntryDto> entries;

}
