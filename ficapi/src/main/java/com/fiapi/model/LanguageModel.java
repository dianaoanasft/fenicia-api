package com.fiapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LanguageModel extends ItemModel {

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "language")
    private Set<CountryModel> countries;

    public static class LanguageModelBuilder extends ItemModel.ItemModelBuilder {
        LanguageModelBuilder() {
            super();
        }

        @Override
        public LanguageModel build() {
            return (LanguageModel) super.build();
        }
    }

}
