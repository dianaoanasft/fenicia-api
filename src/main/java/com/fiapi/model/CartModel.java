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
public class CartModel extends ItemModel {

    @Column(nullable = false, unique = true)
    private String code;

    @OneToOne
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

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartEntryModel> entries;

    @Builder
    public static CartModel.CartModelBuilder builder() {
        return new CartModel.CartModelBuilder();
    }

    public static class CartModelBuilder extends ItemModel.ItemModelBuilder {
        CartModelBuilder() {
            super();
        }
    }

}
