package com.fiapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegionDto extends ItemDto {

    @NotNull
    @Size(min = 2, max = 6)
    private String code;

    @NotNull
    @Size(min = 2, max = 5)
    private String isoCode;

    private String name;
    private String country;

}
