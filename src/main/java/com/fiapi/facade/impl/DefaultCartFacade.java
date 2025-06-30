package com.fiapi.facade.impl;

import com.fiapi.converter.DtoConverter;
import com.fiapi.dto.CartDto;
import com.fiapi.exception.ModelSaveException;
import com.fiapi.facade.CartFacade;
import com.fiapi.model.AddressModel;
import com.fiapi.model.CartModel;
import com.fiapi.model.PaymentMethodModel;
import com.fiapi.model.UserModel;
import com.fiapi.service.AddressService;
import com.fiapi.service.CartService;
import com.fiapi.service.ProductService;
import com.fiapi.service.UserService;
import org.springframework.lang.NonNull;

import java.util.Optional;

public class DefaultCartFacade implements CartFacade {

    private final CartService cartService;
    private final DtoConverter<CartModel, CartDto> cartDtoConverter;

    private final ProductService productService;
    private final UserService userService;

    private final AddressService addressService;

    public DefaultCartFacade(CartService cartService, DtoConverter<CartModel, CartDto> cartDtoConverter,
                             ProductService productService, UserService userService, AddressService addressService) {
        this.cartService = cartService;
        this.cartDtoConverter = cartDtoConverter;
        this.productService = productService;
        this.userService = userService;
        this.addressService = addressService;
    }

    @Override
    public void createCartForCurrentUser() {
        userService.getCurrentUser().ifPresent(cartService::createCartForUser);
    }

    @Override
    public void deleteCartForCurrentUser() {
        userService.getCurrentUser().ifPresent(user ->
                cartService.deleteCar(user.getCart())
        );
    }

    @Override
    public Optional<CartDto> performOrderSimulate(String cartCode) {
        Optional<CartModel> cart = cartService.findCartByCode(cartCode);
        if (cart.isPresent()) {
            Optional<CartModel> simulatedCart = cartService.orderSimulate(cart.get());
            if (simulatedCart.isPresent()) {
                return Optional.of(cartDtoConverter.convert(simulatedCart.get()));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<CartDto> getCartDetailsForCurrentUser() {
        Optional<UserModel> currentUser = userService.getCurrentUser();
        if (currentUser.isPresent()) {
            final UserModel user = currentUser.get();
            return this.getCartDetails(user.getCart().getCode());
        }
        return Optional.empty();
    }

    @Override
    public Optional<CartDto> getCartDetails(String cartCode) {
        Optional<CartModel> cart = cartService.findCartByCode(cartCode);
        if (cart.isPresent()) {
            final CartModel cartModel = cart.get();
            return Optional.of(cartDtoConverter.convert(cartModel));
        }
        return Optional.empty();
    }

    @Override
    public void clearCartForCurrentUser() {
        userService.getCurrentUser()
                .flatMap(cartService::findCartByOwner).ifPresent(cartService::deleteCar);
    }

    @Override
    public void clearCart(String cartCode) {
        cartService.findCartByCode(cartCode).ifPresent(cartService::deleteCar);
    }

    @Override
    public Optional<CartDto> removeItemFromCartForCurrentUser(String productCode) {
        Optional<UserModel> currentUser = userService.getCurrentUser();
        if (currentUser.isPresent()) {
            this.removeItemFromCart(currentUser.get().getCart().getCode(), productCode);
            return this.getCartDetailsForCurrentUser();
        }
        return Optional.empty();
    }

    @Override
    public Optional<CartDto> removeItemFromCart(String cartCode, String productCode) {
        Optional<CartModel> cart = cartService.findCartByCode(cartCode);
        if (cart.isPresent()) {
            CartModel cartModel = cart.get();
            cartModel.getEntries().removeIf(entry -> entry.getProduct().getCode().equals(productCode));
            cartService.orderSimulate(cartModel);
            return Optional.of(cartDtoConverter.convert(cartService.findCartByCode(cartCode).orElseThrow()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<CartDto> updateItemQuantityInCartForCurrentUser(String productCode, int quantity) {
        Optional<UserModel> currentUser = userService.getCurrentUser();
        if (currentUser.isPresent()) {
            Optional<CartModel> cart = cartService.findCartByOwner(currentUser.get());
            if (cart.isPresent()) {
                CartModel cartModel = cart.get();
                cartModel.getEntries().stream()
                        .filter(entry -> entry.getProduct().getCode().equals(productCode))
                        .findFirst()
                        .ifPresent(entry -> entry.setQuantity(quantity));
                cartService.orderSimulate(cartModel);
                return Optional.of(cartDtoConverter.convert(cartService.findCartByCode(cartModel.getCode()).orElseThrow()));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<CartDto> updateItemQuantityInCart(String cartCode, String productCode, int quantity) {
        Optional<CartModel> cart = cartService.findCartByCode(cartCode);
        if (cart.isPresent()) {
            CartModel cartModel = cart.get();
            cartModel.getEntries().stream()
                    .filter(entry -> entry.getProduct().getCode().equals(productCode))
                    .findFirst()
                    .ifPresent(entry -> entry.setQuantity(quantity));
            cartService.orderSimulate(cartModel);
            return Optional.of(cartDtoConverter.convert(cartService.findCartByCode(cartModel.getCode()).orElseThrow()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<CartDto> addItemToCartForCurrentUser(String productCode, int quantity) {
        Optional<UserModel> currentUser = userService.getCurrentUser();
        if (currentUser.isPresent()) {
            Optional<CartModel> cart = cartService.findCartByOwner(currentUser.get());
            if (cart.isPresent()) {
                CartModel cartModel = cart.get();
                productService.findProductByCode(productCode).ifPresent(product -> {
                    cartService.addItemToCart(product, quantity, cartModel);
                    cartService.orderSimulate(cartModel);
                });
                return Optional.of(cartDtoConverter.convert(cartService.findCartByCode(cartModel.getCode()).orElseThrow()));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<CartDto> addItemToCart(String cartCode, String productCode, int quantity) {
        Optional<CartModel> cart = cartService.findCartByCode(cartCode);
        if (cart.isPresent()) {
            CartModel cartModel = cart.get();
            productService.findProductByCode(productCode).ifPresent(product -> {
                cartService.addItemToCart(product, quantity, cartModel);
                cartService.orderSimulate(cartModel);
            });
            return Optional.of(cartDtoConverter.convert(cartService.findCartByCode(cartModel.getCode()).orElseThrow()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<CartDto> setDeliveryAddressOnCurrentCart(@NonNull Long addressId) {
        Optional<UserModel> currentUserOptional = userService.getCurrentUser();
        if (currentUserOptional.isPresent()) {
            final UserModel currentUser = currentUserOptional.get();
            final AddressModel deliveryAddress = validateAndFetchAddressById(addressId, currentUser);
            final CartModel cart = currentUser.getCart();
            cart.setDeliveryAddress(deliveryAddress);
            try {
                cartService.saveCart(cart);
                return Optional.of(cartDtoConverter.convert(cart));
            } catch (ModelSaveException e) {
                return  Optional.empty();
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<CartDto> setPaymentMethodOnCurrentCart(@NonNull PaymentMethodModel paymentMetho) {
        Optional<UserModel> currentUserOptional = userService.getCurrentUser();
        if (currentUserOptional.isPresent()) {
            final UserModel currentUser = currentUserOptional.get();
            final CartModel cart = currentUser.getCart();
            cart.setPaymentMethod(paymentMetho);
            try {
                cartService.saveCart(cart);
                return Optional.of(cartDtoConverter.convert(cart));
            } catch (ModelSaveException e) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    private AddressModel validateAndFetchAddressById(@NonNull Long addressId, @NonNull UserModel user) {
        return addressService.fetchDeliveryAddressByIdAndUser(addressId, user)
                .orElseThrow(() -> new IllegalArgumentException("Address not found or does not belong to the user"));
    }

}
