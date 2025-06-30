package com.fiapi.repository;

import com.fiapi.model.OrderEntryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("orderEntryRepository")
public interface OrderEntryRepository extends JpaRepository<OrderEntryModel, Long> {
}
