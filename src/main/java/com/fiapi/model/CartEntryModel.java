package com.fiapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CartEntryModel extends ItemModel {

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private CartModel cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductModel product;

    private Integer quantity;

    private Double basePrice;
    private Double discountPrice;
    private Double taxPrice;
    private Double totalPrice;

    @Builder
    public static CartEntryModel.CartEntryModelBuilder builder() {
        return new CartEntryModel.CartEntryModelBuilder();
    }

    public static class CartEntryModelBuilder extends ItemModel.ItemModelBuilder {
        CartEntryModelBuilder() {
            super();
        }
    }

}
