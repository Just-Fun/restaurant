package com.serzh.web.rest;

import com.serzh.domain.Item;
import com.serzh.service.dto.ItemRequestDTO;
import com.serzh.service.dto.ItemResponseDTO;
import com.serzh.service.ItemService;
import com.serzh.service.mapper.ItemMapper;
import com.serzh.web.rest.util.PaginationUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api()
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
public class ItemResource {

    private final ItemService itemService;
    private final ItemMapper itemMapper;

    @ApiOperation(value = "Get all items")
    @GetMapping
    public ResponseEntity<List<ItemResponseDTO>> findAllItems(String keyword, Pageable pageable) {
        Page<Item> menus = itemService.findAll(keyword, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(menus, "/api/v1/items");
        List<ItemResponseDTO> itemResponseDTOS = itemMapper.itemsToItemResponseDTOs(menus.getContent());
        return new ResponseEntity<>(itemResponseDTOS, headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Creates a New Item")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemResponseDTO create(@Valid @RequestBody ItemRequestDTO itemRequestDTO) {
        return itemService.create(itemRequestDTO);
    }

    @ApiOperation(value = "Get Item by id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ItemResponseDTO findById(@PathVariable Long id) {
        return itemService.find(id);
    }

    @ApiOperation(value = "Update item")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ItemResponseDTO update(@PathVariable Long id, @Valid @RequestBody ItemRequestDTO itemRequestDTO) {
        return itemService.update(id, itemRequestDTO);
    }

    @ApiOperation(value = "Delete Item")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        itemService.delete(id);
    }

}
