package com.fiapi.model;

import com.fiapi.enums.PaymentType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PaymentMethodModel extends ItemModel {

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private String invoiceNumber;

    private String cardNumber;
    private String cardHolderName;
    private String cardExpiryDate;
    private String cardToken;

    @OneToOne(mappedBy = "paymentMethod")
    private CartModel cart;

    @Builder
    public static PaymentMethodModel.PaymentMethodBuilder builder() {
        return new PaymentMethodModel.PaymentMethodBuilder();
    }

    public static class PaymentMethodBuilder extends ItemModel.ItemModelBuilder {
        PaymentMethodBuilder() {
            super();
        }
    }

}
