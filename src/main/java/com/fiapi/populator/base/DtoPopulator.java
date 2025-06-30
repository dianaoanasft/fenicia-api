package com.fiapi.populator.base;

import com.fiapi.dto.ItemDto;
import com.fiapi.model.ItemModel;

public interface DtoPopulator<S extends ItemModel, T extends ItemDto> {

    void populate(S source, T target);

}
