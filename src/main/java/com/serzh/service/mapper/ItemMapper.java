package com.serzh.service.mapper;

import com.serzh.domain.Item;
import com.serzh.service.dto.ItemRequestDTO;
import com.serzh.service.dto.ItemResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    ItemResponseDTO itemToItemResponseDTO(Item item);

    List<ItemResponseDTO> itemsToItemResponseDTOs(List<Item> items);

    Item itemRequestDTOToItem(ItemRequestDTO itemRequestDTO);

    List<Item> itemDTOsToItems(List<ItemResponseDTO> itemResponseDTOS);

}
