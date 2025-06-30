package com.fiapi.service.impl;

import com.fiapi.exception.ModelSaveException;
import com.fiapi.generator.CartCodeGenerator;
import com.fiapi.model.*;
import com.fiapi.repository.CartEntryRepository;
import com.fiapi.repository.CartRepository;
import com.fiapi.service.CartService;
import com.fiapi.service.OrderSimulateService;
import org.springframework.lang.NonNull;

import java.util.Optional;

public class DefaultCartService implements CartService {

    private final CartRepository cartRepository;
    private final CartEntryRepository cartEntryRepository;
    private final CartCodeGenerator cartCodeGenerator;
    private final OrderSimulateService orderSimulateService;

    public DefaultCartService(CartRepository cartRepository, CartEntryRepository cartEntryRepository, CartCodeGenerator cartCodeGenerator, OrderSimulateService orderSimulateService) {
        this.cartRepository = cartRepository;
        this.cartEntryRepository = cartEntryRepository;
        this.cartCodeGenerator = cartCodeGenerator;
        this.orderSimulateService = orderSimulateService;
    }

    @Override
    public void createCartForUser(@NonNull UserModel user) {
        findCartByOwner(user).ifPresentOrElse(cart -> {
            throw new IllegalStateException("Cart already exists for user: " + user.getUsername());
        }, () -> {
            final CartModel cart = generateCartModel(user);
            cartRepository.save(cart);
        });
    }

    @Override
    public void deleteCar(@NonNull CartModel cart) {
        cartRepository.delete(cart);
    }

    @Override
    public Optional<CartModel> addItemToCart(ProductModel product, int quantity, CartModel cart) {
        cart.getEntries().stream()
                .filter(entry -> entry.getProduct().equals(product))
                .findFirst()
                .ifPresentOrElse(entry ->
                                entry.setQuantity(entry.getQuantity() + quantity)
                        , () -> {
                            CartEntryModel newEntry = new CartEntryModel();
                            newEntry.setProduct(product);
                            newEntry.setQuantity(quantity);
                            newEntry.setCart(cart);
                            cartEntryRepository.save(newEntry);
                            cart.getEntries().add(newEntry);
                            cartRepository.save(cart);
                        });
        return orderSimulate(cart);
    }

    @Override
    public Optional<CartModel> orderSimulate(@NonNull CartModel cart) {
        orderSimulateService.performOrderSimulate(cart);
        cartEntryRepository.saveAll(cart.getEntries());
        return Optional.of(cartRepository.save(cart));
    }

    @Override
    public Optional<CartModel> setDeliveryAddress(AddressModel address, CartModel cart) {
        cart.setDeliveryAddress(address);
        return Optional.of(cartRepository.save(cart));
    }

    @Override
    public Optional<CartModel> setPaymentMethod(PaymentMethodModel paymentMethod, CartModel cart) {
        cart.setPaymentMethod(paymentMethod);
        return Optional.of(cartRepository.save(cart));
    }

    @Override
    public Optional<CartModel> findCartByOwner(@NonNull UserModel user) {
        final CartModel cart = cartRepository.findByOwner(user);
        return Optional.ofNullable(cart);
    }

    @Override
    public Optional<CartModel> findCartByCode(@NonNull String code) {
        final CartModel cart = cartRepository.findByCode(code);
        return Optional.ofNullable(cart);
    }

    @Override
    public void saveCart(CartModel cart) throws ModelSaveException {
        try {
            cartRepository.save(cart);
        } catch (Exception e) {
            throw new ModelSaveException("Failed to save cart: " + cart.getCode(), e);
        }
    }

    private CartModel generateCartModel(@NonNull UserModel user) {
        final CartModel cartModel = new CartModel();
        cartModel.setCode(cartCodeGenerator.generateCartCode());
        cartModel.setCreationTime(new java.util.Date());
        cartModel.setModifiedTime(new java.util.Date());
        cartModel.setOwner(user);

        return cartModel;
    }

}
