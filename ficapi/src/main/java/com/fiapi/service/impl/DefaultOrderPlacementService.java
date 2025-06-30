package com.fiapi.service.impl;

import com.fiapi.model.CartEntryModel;
import com.fiapi.model.CartModel;
import com.fiapi.model.OrderEntryModel;
import com.fiapi.model.OrderModel;
import com.fiapi.repository.OrderRepository;
import com.fiapi.service.CartService;
import com.fiapi.service.OrderPlacementService;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DefaultOrderPlacementService implements OrderPlacementService {

    private final OrderRepository orderRepository;
    private final CartService cartService;

    public DefaultOrderPlacementService(OrderRepository orderRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
    }

    @Override
    public void saveOrder(@NonNull OrderModel order) {
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public OrderModel placeOrder(@NonNull CartModel cart) {
        final OrderModel order = cloneCartToOrder(cart);
        this.saveOrder(order);
        cartService.deleteCar(cart);
        return order;
    }

    private OrderModel cloneCartToOrder(@NonNull CartModel cart) {
        final OrderModel order = new OrderModel();
        order.setCode(generateOrderCode());
        order.setOwner(cart.getOwner());
        order.setBasePrice(cart.getBasePrice());
        order.setDiscountPrice(cart.getDiscountPrice());
        order.setDiscountPrice(cart.getDiscountPrice());
        order.setTaxPrice(cart.getTaxPrice());
        order.setTotalPrice(cart.getTotalPrice());
        order.setCreationTime(new Date());

        final List<OrderEntryModel> orderEntries = new ArrayList<>();
        cart.getEntries().forEach(entry -> {
            final OrderEntryModel orderEntry = cloneCartEntryToOrderEntry(order, entry);
            orderEntries.add(orderEntry);
        });
        order.setEntries(orderEntries);
        order.setDeliveryAddress(cart.getDeliveryAddress());
        order.setPaymentMethod(cart.getPaymentMethod());

        return order;
    }

    private OrderEntryModel cloneCartEntryToOrderEntry(@NonNull OrderModel order, @NonNull CartEntryModel cartEntry) {
        final OrderEntryModel orderEntry = new OrderEntryModel();
        orderEntry.setOrder(order);
        orderEntry.setProduct(cartEntry.getProduct());
        orderEntry.setQuantity(cartEntry.getQuantity());
        orderEntry.setBasePrice(cartEntry.getBasePrice());
        orderEntry.setDiscountPrice(cartEntry.getDiscountPrice());
        orderEntry.setTaxPrice(cartEntry.getTaxPrice());
        orderEntry.setTotalPrice(cartEntry.getTotalPrice());

        return orderEntry;
    }

    private String generateOrderCode() {
        return "ORD-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 10000);
    }

}
