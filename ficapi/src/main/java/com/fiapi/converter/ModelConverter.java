package com.fiapi.converter;

import com.fiapi.dto.ItemDto;
import com.fiapi.model.ItemModel;

import java.util.List;

public interface ModelConverter<S extends ItemDto, T extends ItemModel> {

    T convert(S source);

    List<T> convertAll(List<S> sources);


}
