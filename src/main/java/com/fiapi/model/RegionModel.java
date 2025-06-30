package com.fiapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class RegionModel extends ItemModel {

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false, unique = true)
    private String isoCode;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "country_pk")
    private CountryModel country;

    public boolean equals(RegionModel region) {
        if (Objects.nonNull(region.getPk())) {
            return this.getPk().equals(region.getPk());
        }
        return this.code.equals(region.getCode());
    }

    public static class RegionModelBuilder extends ItemModel.ItemModelBuilder {
        RegionModelBuilder() {
            super();
        }

        @Override
        public RegionModel build() {
            return (RegionModel) super.build();
        }
    }


}
