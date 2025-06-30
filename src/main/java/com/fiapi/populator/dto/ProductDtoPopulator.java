package com.fiapi.populator.dto;

import com.fiapi.dto.ProductDto;
import com.fiapi.model.ProductModel;
import com.fiapi.populator.base.DtoPopulator;
import org.springframework.lang.NonNull;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ProductDtoPopulator implements DtoPopulator<ProductModel, ProductDto> {

    @Override
    public void populate(ProductModel source, ProductDto target) {
        populateIfNonNull(source::getCode, target::setSku);
        populateIfNonNull(source::getName, target::setName);
        populateIfNonNull(source::getDescription, target::setDescription);
        if (Objects.nonNull(source.getStatus())) {
            target.setStatus(source.getStatus().name());
        }
        populateIfNonNull(source::getWeight, target::setWeight);
        populateIfNonNull(source::getBasePrice, target::setBasePrice);
        populateIfNonNull(source::getDiscountPrice, target::setDiscountPrice);
        populateIfNonNull(source::getTaxPrice, target::setTaxPrice);
        calculateTotalPrice(target);
        if (Objects.nonNull(source.getCountry())) {
            target.setCountry(source.getCountry().getCode());
        }
        if (Objects.nonNull(source.getCountry()) && Objects.nonNull(source.getCountry().getCurrency())) {
            target.setCurrency(source.getCountry().getCurrency().getCode());
        }
        populateIfNonNull(source::getImageUrl, target::setImageUrl);
        populateIfNonNull(source::getPackingQuantity, target::setPackageQuantity);
        populateIfNonNull(source::getBrand, target::setBrand);
        populateIfNonNull(source::getCategory, target::setCategory);
    }

    private void calculateTotalPrice(@NonNull ProductDto target) {
        // calculate total price based on base price discount tax price
        Double totalPrice = target.getBasePrice();
        if (Objects.nonNull(target.getTaxPrice())) {
            totalPrice += target.getTaxPrice();
        }
        if (Objects.nonNull(target.getDiscountPrice())) {
            totalPrice -= target.getDiscountPrice();
        }
        totalPrice = Double.valueOf(String.format("%.2f", totalPrice));
        target.setTotalPrice(totalPrice);
        target.setTotalPrice(totalPrice);
    }

    public <T> void populateIfNonNull(Supplier<T> valueSupplier, Consumer<T> setterConsumer) {
        T value = valueSupplier.get();
        if (Objects.nonNull(value)) {
            setterConsumer.accept(value);
        }
    }

}
