package com.serzh.web.rest;

import com.serzh.service.dto.TypeDTO;
import com.serzh.service.impl.TypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api()
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/types")
public class TypeResource {

    private final TypeService typeService;

    @ApiOperation(value = "Get all types")
    @GetMapping
    public List<TypeDTO> findAllTypes() {
        return typeService.findAll();
    }

}
