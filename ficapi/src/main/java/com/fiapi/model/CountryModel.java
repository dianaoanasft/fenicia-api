package com.fiapi.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CountryModel extends ItemModel {

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false, unique = true)
    private String isoCode;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<RegionModel> regions;

    @ManyToOne(cascade = CascadeType.ALL)
    private LanguageModel language;

    @ManyToOne(cascade = CascadeType.ALL)
    private CurrencyModel currency;

    public boolean equals(CountryModel country) {
        if (Objects.nonNull(country.getPk())) {
            return this.getPk().equals(country.getPk());
        }
        return this.code.equals(country.getCode());
    }

    public static class CountryModelBuilder extends ItemModel.ItemModelBuilder {
        CountryModelBuilder() {
            super();
        }

        @Override
        public CountryModel build() {
            return (CountryModel) super.build();
        }
    }

}
