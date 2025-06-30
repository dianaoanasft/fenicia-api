package com.fiapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDto extends ItemDto{

    private String alias;
    private String street;
    private String number;
    private String postalCode;
    private String phoneNumber;
    private String town;
    private String additionalInformation;
    private String country;

    @JsonProperty("isDefaultAddress")
    private boolean isDefaultAddress;

    @JsonProperty("isBillingAddress")
    private boolean isBillingAddress;

    @JsonProperty("isDeliveryAddress")
    private boolean isDeliveryAddress;

    private String region;

}
