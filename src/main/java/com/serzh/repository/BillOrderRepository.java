package com.serzh.repository;

import com.serzh.domain.BillOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillOrderRepository extends JpaRepository<BillOrder, Long> {

}
