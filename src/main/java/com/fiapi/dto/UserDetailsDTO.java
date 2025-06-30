package com.fiapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDetailsDTO extends ItemDto {

    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private LanguageDto currentLanguage;

}
