package com.fiapi.facade;

import com.fiapi.dto.CartDto;
import com.fiapi.model.PaymentMethodModel;

import java.util.Optional;

public interface CartFacade {

    void createCartForCurrentUser();

    void deleteCartForCurrentUser();

    Optional<CartDto> performOrderSimulate(String cartCode);

    Optional<CartDto> getCartDetailsForCurrentUser();

    Optional<CartDto> getCartDetails(String cartCode);

    void clearCartForCurrentUser();

    void clearCart(String cartCode);

    Optional<CartDto> removeItemFromCartForCurrentUser(String productCode);

    Optional<CartDto> removeItemFromCart(String cartCode, String productCode);

    Optional<CartDto> updateItemQuantityInCartForCurrentUser(String productCode, int quantity);

    Optional<CartDto> updateItemQuantityInCart(String cartCode, String productCode, int quantity);

    Optional<CartDto> addItemToCartForCurrentUser(String productCode, int quantity);

    Optional<CartDto> addItemToCart(String cartCode, String productCode, int quantity);

    Optional<CartDto> setDeliveryAddressOnCurrentCart(Long addressId);

    Optional<CartDto> setPaymentMethodOnCurrentCart(PaymentMethodModel paymentMetho);

}
