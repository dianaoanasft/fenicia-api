package com.fiapi.populator.base;

import com.fiapi.dto.ItemDto;
import com.fiapi.model.ItemModel;

public interface ModelPopulator<S extends ItemDto, T extends ItemModel> {

    void populate(S source, T target);

}
