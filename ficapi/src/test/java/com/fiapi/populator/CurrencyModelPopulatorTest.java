package com.fiapi.populator;

import com.fiapi.dto.CurrencyDto;
import com.fiapi.model.CurrencyModel;
import com.fiapi.populator.model.CurrencyModelPopulator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CurrencyModelPopulatorTest {

    private CurrencyModelPopulator currencyModelPopulator;

    @BeforeEach
    void setUp() {
        currencyModelPopulator = new CurrencyModelPopulator();
    }

    @Test
    void testPopulate() {
        CurrencyDto currencyDto = new CurrencyDto();
        currencyDto.setCode("USD");
        currencyDto.setName("Dollar");

        CurrencyModel currencyModel = new CurrencyModel();

        currencyModelPopulator.populate(currencyDto, currencyModel);

        assertEquals("USD", currencyModel.getCode());
        assertEquals("Dollar", currencyModel.getName());
    }
}