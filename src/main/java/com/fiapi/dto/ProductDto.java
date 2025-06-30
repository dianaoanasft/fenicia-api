package com.fiapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto extends ItemDto {

    private String sku;
    private String name;
    private String description;
    private String status;
    private Double weight;
    private Double basePrice;
    private Double discountPrice;
    private Double taxPrice;
    private Double totalPrice;
    private String currency;
    private String imageUrl;
    private Integer packageQuantity;
    private String country;
    private String brand;
    private String category;

}
