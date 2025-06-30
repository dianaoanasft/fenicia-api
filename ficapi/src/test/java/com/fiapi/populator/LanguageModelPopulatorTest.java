package com.fiapi.populator;

import com.fiapi.dto.LanguageDto;
import com.fiapi.model.LanguageModel;
import com.fiapi.populator.model.LanguageModelPopulator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LanguageModelPopulatorTest {

    private LanguageModelPopulator languageModelPopulator;

    @BeforeEach
    void setUp() {
        languageModelPopulator = new LanguageModelPopulator();
    }

    @Test
    void testPopulate() {
        LanguageDto languageDto = new LanguageDto();
        languageDto.setCode("EN");
        languageDto.setName("English");

        LanguageModel languageModel = new LanguageModel();

        languageModelPopulator.populate(languageDto, languageModel);

        assertEquals("EN", languageModel.getCode());
        assertEquals("English", languageModel.getName());
    }
}