package com.fiapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountryDto extends ItemDto {

    private String code;
    private String isoCode;
    private String name;

    private List<RegionDto> regions;
    private LanguageDto language;
    private CurrencyDto currency;

}
