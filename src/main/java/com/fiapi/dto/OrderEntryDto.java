package com.fiapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderEntryDto extends ItemDto {

    private ProductDto product;
    private Integer quantity;
    private Double basePrice;
    private Double discountPrice;
    private Double taxPrice;
    private Double totalPrice;

}
