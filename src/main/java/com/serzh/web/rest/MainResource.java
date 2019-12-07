package com.serzh.web.rest;

import com.serzh.service.MainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api()
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/main")
public class MainResource {

    private final MainService mainService;

    @ApiOperation(value = "save")
    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public void save() {
        mainService.save();
    }

    @ApiOperation(value = "get")
    @PostMapping("/get/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void get(@PathVariable Integer id) {
        mainService.get(id);
    }

    @ApiOperation(value = "update")
    @PostMapping("/update/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void update(@PathVariable Integer id) {
        mainService.update(id);
    }



}
