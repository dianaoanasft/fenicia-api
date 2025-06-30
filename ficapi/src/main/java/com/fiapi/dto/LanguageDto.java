package com.fiapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LanguageDto extends ItemDto {

    @NotNull
    @Size(min = 2, max = 5)
    private String code;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    private Set<String> countries;

}