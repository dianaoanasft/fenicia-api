package com.fiapi.repository;

import com.fiapi.model.CartEntryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("cartEntryRepository")
public interface CartEntryRepository extends JpaRepository<CartEntryModel, Long> {
}
