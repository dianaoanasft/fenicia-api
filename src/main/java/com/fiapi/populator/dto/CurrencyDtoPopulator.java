package com.fiapi.populator.dto;

import com.fiapi.dto.CurrencyDto;
import com.fiapi.model.CountryModel;
import com.fiapi.model.CurrencyModel;
import com.fiapi.populator.base.DtoPopulator;
import org.apache.commons.collections4.CollectionUtils;

import java.util.stream.Collectors;

public class CurrencyDtoPopulator implements DtoPopulator<CurrencyModel, CurrencyDto> {

    @Override
    public void populate(CurrencyModel source, CurrencyDto target) {
        target.setCode(source.getCode());
        target.setName(source.getName());

        if (CollectionUtils.isNotEmpty(source.getCountries())) {
            target.setCountries(source.getCountries().stream()
                    .map(CountryModel::getCode)
                    .collect(Collectors.toSet()));
        }
    }
}
