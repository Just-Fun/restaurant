package com.serzh.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillResponseDTO {

    private Long id;
    private BigDecimal totalPrice;
    private Set<OrderUnitDTO> orderUnits;

}
