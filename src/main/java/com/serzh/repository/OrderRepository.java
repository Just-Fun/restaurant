package com.serzh.repository;

import com.serzh.domain.OrderUnit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderUnit, Long> {

}
