package com.serzh.service.impl;

import com.serzh.service.dto.BillResponseDTO;
import com.serzh.service.dto.OrderRequestDTO;

import java.util.List;

public interface OrderService {
    Long createOrder(List<OrderRequestDTO> orderRequestDTOs);

    void updateOrder(Long id, List<OrderRequestDTO> orderRequest);

    BillResponseDTO check(Long id);
}
