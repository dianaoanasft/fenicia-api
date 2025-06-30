package com.fiapi.repository;

import com.fiapi.model.PaymentMethodModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("paymentMethodRepository")
public interface PaymentMethodRepository extends JpaRepository<PaymentMethodModel, Long> {
}
