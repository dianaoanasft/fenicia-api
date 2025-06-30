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
public class OrderEntryModel  extends ItemModel {

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderModel order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductModel product;

    private Integer quantity;

    private Double basePrice;
    private Double discountPrice;
    private Double taxPrice;
    private Double totalPrice;

    @Builder
    public static OrderEntryModel.OrderEntryModelBuilder builder() {
        return new OrderEntryModel.OrderEntryModelBuilder();
    }

    public static class OrderEntryModelBuilder extends ItemModel.ItemModelBuilder {
        OrderEntryModelBuilder() {
            super();
        }
    }
}
