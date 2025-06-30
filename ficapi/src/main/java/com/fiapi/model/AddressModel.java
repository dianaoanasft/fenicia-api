package com.fiapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AddressModel extends ItemModel {

    private String alias;
    private String street;
    private String number;
    private String postalCode;
    private String phoneNumber;
    private String town;

    private String additionalInformation;

    private boolean isDeliveryAddress;
    private boolean isBillingAddress;
    private boolean isDefaultAddress;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "region_id")
    private RegionModel region;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "country_id")
    private CountryModel country;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserModel user;

    @Override
    public String toString() {
        return "AddressModel{" +
                "alias='" + alias + '\'' +
                ", street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", town='" + town + '\'' +
                ", additionalInformation='" + additionalInformation + '\'' +
                ", isDeliveryAddress=" + isDeliveryAddress +
                ", isBillingAddress=" + isBillingAddress +
                ", isDefaultAddress=" + isDefaultAddress +
                ", region=" + region +
                ", country=" + country +
                ", user=" + user +
                '}';
    }

    public boolean equals(AddressModel address) {
        if (Objects.nonNull(address.getPk())) {
            return address.getPk().equals(getPk());
        }
        if (Objects.nonNull(address.getUser()) && Objects.nonNull(this.getUser()) && address.getUser().getPk().equals(this.getUser().getPk())) {
            return Objects.equals(address.getCountry(), this.getCountry())
                    && Objects.equals(address.getRegion(), this.getRegion())
                    && Objects.equals(address.getAlias(), this.getAlias())
                    && Objects.equals(address.getStreet(), this.getStreet())
                    && Objects.equals(address.getNumber(), this.getNumber())
                    && Objects.equals(address.getPostalCode(), this.getPostalCode())
                    && Objects.equals(address.getPhoneNumber(), this.getPhoneNumber())
                    && Objects.equals(address.getTown(), this.getTown());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAlias(), getStreet(), getNumber(), getPostalCode(), getPhoneNumber(), getTown(), getAdditionalInformation(), isDeliveryAddress(), isBillingAddress(), isDefaultAddress(), getRegion(), getCountry(), getUser());
    }

    public static class AddressModelBuilder extends ItemModel.ItemModelBuilder {
        AddressModelBuilder() {
            super();
        }

        @Override
        public AddressModel build() {
            return (AddressModel) super.build();
        }
    }

}
