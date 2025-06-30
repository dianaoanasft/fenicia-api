package com.fiapi.populator.model;

import com.fiapi.dto.CurrencyDto;
import com.fiapi.model.CurrencyModel;
import com.fiapi.populator.base.ModelPopulator;

public class CurrencyModelPopulator implements ModelPopulator<CurrencyDto, CurrencyModel> {

    @Override
    public void populate(CurrencyDto source, CurrencyModel target) {
        target.setCode(source.getCode());
        target.setName(source.getName());
    }
}
