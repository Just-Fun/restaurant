package com.serzh.web.rest;

import com.serzh.service.dto.BillResponseDTO;
import com.serzh.service.dto.OrderRequestDTO;
import com.serzh.service.impl.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderResource {

    private final OrderService orderService;

    @ApiOperation(value = "Creates a New Order")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@Valid @RequestBody List<OrderRequestDTO> orderRequestDTOs) {
        return orderService.createOrder(orderRequestDTOs);
    }

    @ApiOperation(value = "Update Order")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void addItem(@PathVariable Long id, @Valid @RequestBody List<OrderRequestDTO> orderRequestDTOs) {
        orderService.updateOrder(id, orderRequestDTOs);
    }

    @ApiOperation(value = "Check Bill Order by id")
    @GetMapping("/{id}/check")
    @ResponseStatus(HttpStatus.OK)
    public BillResponseDTO check(@PathVariable Long id) {
        return orderService.check(id);
    }

}
