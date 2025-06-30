package com.fiapi.converter;

import com.fiapi.dto.ItemDto;
import com.fiapi.model.ItemModel;

import java.util.List;

public interface DtoConverter<S extends ItemModel, T extends ItemDto> {

    T convert(S source);

    List<T> convertAll(List<S> sources);

}
