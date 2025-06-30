package com.fiapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CurrencyModel extends ItemModel {

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "currency")
    private Set<CountryModel> countries;

    public static class CurrencyModelBuilder extends ItemModel.ItemModelBuilder {
        CurrencyModelBuilder() {
            super();
        }

        @Override
        public CurrencyModel build() {
            return (CurrencyModel) super.build();
        }
    }


}