package com.serzh.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderUnitDTO {

    private String menu;
    private Integer quantity;
    private LocalDateTime orderedTime;

}
