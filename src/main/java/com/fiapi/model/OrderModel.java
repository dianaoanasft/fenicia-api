package com.fiapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderModel extends ItemModel {

    @Column(nullable = false, unique = true)
    private String code;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserModel owner;

    private Double basePrice;
    private Double discountPrice;
    private Double taxPrice;
    private Double totalPrice;

    private Date creationTime;
    private Date modifiedTime;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private AddressModel deliveryAddress;

    @OneToOne
    @JoinColumn(name = "paymentmethod_id")
    private PaymentMethodModel paymentMethod;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderEntryModel> entries;

    @Builder
    public static OrderModel.OrderModelBuilder builder() {
        return new OrderModel.OrderModelBuilder();
    }

    public static class OrderModelBuilder extends ItemModel.ItemModelBuilder {
        OrderModelBuilder() {
            super();
        }
    }
    
}
