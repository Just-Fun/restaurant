package com.serzh.repository;

import com.serzh.domain.OrderUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface OrderUnitRepository extends JpaRepository<OrderUnit, Long> {

    void deleteByBillOrder_IdAndItem_Id(Long id, Long itemId);

    void deleteByBillOrder_IdAndItem_IdIn(Long id, Set<Long> itemIds);

}
