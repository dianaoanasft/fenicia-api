package com.fiapi.service.impl;

import com.fiapi.model.CartEntryModel;
import com.fiapi.model.CartModel;
import com.fiapi.model.ProductModel;
import com.fiapi.service.OrderSimulateService;
import org.springframework.lang.NonNull;

import java.util.Objects;

public class DefaultOrderSimulateService implements OrderSimulateService {

    @Override
    public void performOrderSimulate(@NonNull CartModel cart) {
        cart.getEntries().forEach(this::updateCartEntriesPrices);
        final Double totalBasePrice = cart.getEntries().stream()
                .mapToDouble(CartEntryModel::getBasePrice)
                .sum();
        final Double totalDiscountPrice = cart.getEntries().stream()
                .mapToDouble(CartEntryModel::getDiscountPrice)
                .sum();
        final Double totalTaxPrice = cart.getEntries().stream()
                .mapToDouble(CartEntryModel::getTaxPrice)
                .sum();
        final Double totalPrice = totalBasePrice - totalDiscountPrice + totalTaxPrice;
        cart.setBasePrice(totalBasePrice);
        cart.setDiscountPrice(totalDiscountPrice);
        cart.setTaxPrice(totalTaxPrice);
        cart.setTotalPrice(totalPrice);
    }

    private void updateCartEntriesPrices(@NonNull CartEntryModel cartEntry) {
        final ProductModel product = cartEntry.getProduct();
        final Integer quantity = cartEntry.getQuantity();
        if (Objects.nonNull(product) && quantity > 0) {
            final Double basePrice = product.getBasePrice();
            final Double discountPrice = product.getDiscountPrice();
            final Double taxPrice = product.getTaxPrice();
            cartEntry.setBasePrice(basePrice * quantity);
            cartEntry.setDiscountPrice(discountPrice * quantity);
            cartEntry.setTaxPrice(taxPrice * quantity);
            cartEntry.setTotalPrice(cartEntry.getBasePrice() - cartEntry.getDiscountPrice() + cartEntry.getTaxPrice());
        }
    }

}
