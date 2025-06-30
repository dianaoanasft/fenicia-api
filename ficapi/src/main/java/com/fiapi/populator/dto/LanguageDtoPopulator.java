package com.fiapi.populator.dto;

import com.fiapi.dto.LanguageDto;
import com.fiapi.model.CountryModel;
import com.fiapi.model.LanguageModel;
import com.fiapi.populator.base.DtoPopulator;
import org.apache.commons.collections4.CollectionUtils;

import java.util.stream.Collectors;

public class LanguageDtoPopulator implements DtoPopulator<LanguageModel, LanguageDto> {

    @Override
    public void populate(LanguageModel source, LanguageDto target) {
        target.setCode(source.getCode());
        target.setName(source.getName());

        if (CollectionUtils.isNotEmpty(source.getCountries())) {
            target.setCountries(source.getCountries().stream()
                    .map(CountryModel::getCode)
                    .collect(Collectors.toSet()));
        }
    }

}
