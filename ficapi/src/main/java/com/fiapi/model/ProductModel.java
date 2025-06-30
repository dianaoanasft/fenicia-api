package com.fiapi.model;

import com.fiapi.enums.StatusEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProductModel extends ItemModel {

    private String code;
    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    private Double weight;
    private Double basePrice;
    private Double discountPrice;
    private Double taxPrice;

    private String imageUrl;

    private Integer packingQuantity;

    private String category;
    private String brand;

    @ManyToOne
    @JoinColumn(name = "country_id") // Maps the country by its ID
    private CountryModel country;

    public static class ProductModelBuilder extends ItemModel.ItemModelBuilder {
        ProductModelBuilder() {
            super();
        }

        @Override
        public ProductModel build() {
            return (ProductModel) super.build();
        }
    }

}
