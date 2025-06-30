package com.fiapi.repository;

import com.fiapi.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("orderRepository")
public interface OrderRepository extends JpaRepository<OrderModel, Long> {

    @Query("SELECT o FROM OrderModel o WHERE o.owner.pk = :userId")
    List<OrderModel> findAllByUserId(@Param("userId") Long userId);

    @Query("SELECT o FROM OrderModel o WHERE o.code = :code")
    OrderModel findByCode(@Param("code") String code);

}
