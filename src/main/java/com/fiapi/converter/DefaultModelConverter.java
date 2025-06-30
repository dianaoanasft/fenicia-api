package com.fiapi.converter;

import com.fiapi.dto.ItemDto;
import com.fiapi.exception.ConversionException;
import com.fiapi.model.ItemModel;
import com.fiapi.populator.base.ModelPopulator;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class DefaultModelConverter<S extends ItemDto, T extends ItemModel> implements ModelConverter<S, T> {

    private final List<ModelPopulator<S, T>> populators;
    private final Class<T> dtoClass;

    public DefaultModelConverter(List<ModelPopulator<S, T>> populators, Class<T> dtoClass) {
        this.populators = populators;
        this.dtoClass = dtoClass;
    }

    @Override
    public T convert(S source) {
        T target = createDtoInstance();
        populators.forEach(populator -> populator.populate(source, target));
        return target;
    }

    @Override
    public List<T> convertAll(List<S> source) {
        List<T> targets = new ArrayList<>();
        source.forEach(itemModel -> targets.add(convert(itemModel)));
        return targets;
    }

    private T createDtoInstance() {
        try {
            return dtoClass.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new ConversionException("Error creating instance of " + dtoClass.getName(), e);
        }
    }
}
