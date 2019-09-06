package com.serzh.service.impl;

import com.serzh.domain.Item;
import com.serzh.repository.ItemRepository;
import com.serzh.repository.TypeRepository;
import com.serzh.service.ItemService;
import com.serzh.service.dto.ItemRequestDTO;
import com.serzh.service.dto.ItemResponseDTO;
import com.serzh.service.mapper.ItemMapper;
import com.serzh.service.component.ItemSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Optional;

import static com.serzh.service.util.Constants.ITEM_WAS_NOT_FOUND_BY_ID;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final TypeRepository typeRepository;
    private final ItemSpecification itemSpecification;
    private final ItemMapper itemMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<Item> findAll(String keyword, Pageable pageable) {
        return itemRepository.findAll(itemSpecification.getFilter(keyword), pageable);
    }

    @Override
    public ItemResponseDTO create(ItemRequestDTO itemRequest) {
        Item item = itemMapper.itemRequestDTOToItem(itemRequest);

        Optional.ofNullable(itemRequest.getTypeIds())
                .map(typeRepository::findAllById)
                .ifPresent(types -> item.setTypes(new HashSet<>(types)));

        Item savedItem = itemRepository.save(item);

        return itemMapper.itemToItemResponseDTO(savedItem);
    }

    @Override
    @Transactional(readOnly = true)
    public ItemResponseDTO find(Long id) {
        return itemMapper.itemToItemResponseDTO(itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ITEM_WAS_NOT_FOUND_BY_ID, id))));
    }

    @Override
    public ItemResponseDTO update(Long id, ItemRequestDTO itemRequest) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ITEM_WAS_NOT_FOUND_BY_ID, id)));
//        TODO check if updated item is different from request, if not - don't do anything
        item.setName(itemRequest.getName());
        item.setDescription(itemRequest.getDescription());
        item.setImage(itemRequest.getImage());
        item.setPrice(itemRequest.getPrice());

//        TODO before update, check if updated item contains all typeIds, if yes - don't do anything
        Optional.ofNullable(itemRequest.getTypeIds())
                .map(typeRepository::findAllById)
                .ifPresent(types -> item.setTypes(new HashSet<>(types)));

        Item save = itemRepository.save(item);

        return itemMapper.itemToItemResponseDTO(save);

    }

    @Override
    public void delete(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ITEM_WAS_NOT_FOUND_BY_ID, id)));
        itemRepository.delete(item);
    }
}
