package com.serzh.service;

import com.serzh.domain.Item;
import com.serzh.service.dto.ItemRequestDTO;
import com.serzh.service.dto.ItemResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemService {

    Page<Item> findAll(String keyword, Pageable pageable);

    ItemResponseDTO create(ItemRequestDTO itemRequestDTO);

    ItemResponseDTO find(Long id);

    ItemResponseDTO update(Long id, ItemRequestDTO itemRequestDTO);

    void delete(Long id);

}