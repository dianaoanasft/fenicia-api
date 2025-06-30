package com.fiapi.populator.dto;

import com.fiapi.converter.DtoConverter;
import com.fiapi.dto.LanguageDto;
import com.fiapi.dto.UserDetailsDTO;
import com.fiapi.model.LanguageModel;
import com.fiapi.model.UserModel;
import com.fiapi.populator.base.DtoPopulator;

public class UserDetailsDtoPopulator implements DtoPopulator<UserModel, UserDetailsDTO> {

    private final DtoConverter<LanguageModel, LanguageDto> languageDtoConverter;

    public UserDetailsDtoPopulator(DtoConverter<LanguageModel, LanguageDto> languageDtoConverter) {
        this.languageDtoConverter = languageDtoConverter;
    }

    @Override
    public void populate(UserModel source, UserDetailsDTO target) {
        target.setEmail(source.getEmail());
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setRole(source.getRole().name());
        target.setCurrentLanguage(languageDtoConverter.convert(source.getCurrentLanguage()));
    }
}
