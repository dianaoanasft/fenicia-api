package com.fiapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CurrencyDto extends ItemDto {

    private String code;
    private String name;
    private Set<String> countries;

}
