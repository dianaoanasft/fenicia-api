package com.fiapi.populator.model;

import com.fiapi.dto.LanguageDto;
import com.fiapi.model.LanguageModel;
import com.fiapi.populator.base.ModelPopulator;

public class LanguageModelPopulator implements ModelPopulator<LanguageDto, LanguageModel> {

    @Override
    public void populate(LanguageDto source, LanguageModel target) {
        target.setCode(source.getCode());
        target.setName(source.getName());
    }

}
