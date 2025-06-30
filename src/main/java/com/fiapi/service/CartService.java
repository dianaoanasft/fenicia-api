package com.fiapi.service;

import com.fiapi.exception.ModelSaveException;
import com.fiapi.model.*;

import java.util.Optional;

public interface CartService {

    void createCartForUser(UserModel user);

    void deleteCar(CartModel cart);

    Optional<CartModel> addItemToCart(ProductModel product, int quantity, CartModel cart);

    Optional<CartModel> orderSimulate(CartModel cart);

    Optional<CartModel> setDeliveryAddress(AddressModel address, CartModel cart);

    Optional<CartModel> setPaymentMethod(PaymentMethodModel paymentMethod, CartModel cart);

    Optional<CartModel> findCartByOwner(UserModel user);

    Optional<CartModel> findCartByCode(String code);

    void saveCart(CartModel cart) throws ModelSaveException;

}
