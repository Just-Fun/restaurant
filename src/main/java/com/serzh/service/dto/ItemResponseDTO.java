package com.serzh.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponseDTO {

    private Long id;
    private String name;
    private String description;
    private String image;
    private BigDecimal price;
    private Set<TypeDTO> types = new HashSet<>();

}
