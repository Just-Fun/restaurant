package com.serzh.service.impl;

import com.serzh.domain.BillOrder;
import com.serzh.domain.Item;
import com.serzh.domain.OrderUnit;
import com.serzh.repository.BillOrderRepository;
import com.serzh.repository.ItemRepository;
import com.serzh.repository.OrderUnitRepository;
import com.serzh.service.component.OrderConverter;
import com.serzh.service.dto.BillResponseDTO;
import com.serzh.service.dto.OrderRequestDTO;
import com.serzh.service.mapper.OrderUnitMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.serzh.service.util.Constants.ITEM_WAS_NOT_FOUND_BY_ID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderUnitRepository orderUnitRepository;
    private final BillOrderRepository billOrderRepository;
    private final ItemRepository itemRepository;
    private final OrderConverter orderConverter;
    private final OrderUnitMapper orderUnitMapper;

    @Override
    public Long createOrder(List<OrderRequestDTO> orderRequestDTOs) {
        BillOrder billOrder = billOrderRepository.save(new BillOrder());

        Set<Long> itemIds = orderRequestDTOs.stream().mapToLong(OrderRequestDTO::getItemId).boxed().collect(Collectors.toSet());

        List<Item> items = itemRepository.findAllById(itemIds);

        List<OrderUnit> orderUnits = orderRequestDTOs.stream()
                .map(payload -> orderConverter.toEntity(payload, billOrder, items)).collect(Collectors.toList());

        orderUnitRepository.saveAll(orderUnits);
        return billOrder.getId();
    }


    @Override
    public void updateOrder(Long id, List<OrderRequestDTO> orderRequestDTOs) {
        BillOrder billOrder = getBillOrder(id);

        Set<OrderUnit> oldOrderUnits = billOrder.getOrderUnits();
        Set<Long> oldItemIds = oldOrderUnits.stream().map(OrderUnit::getItem)
                .mapToLong(Item::getId).boxed().collect(Collectors.toSet());

        Set<Long> lastItemIds = orderRequestDTOs.stream().mapToLong(OrderRequestDTO::getItemId).boxed().collect(Collectors.toSet());

        oldOrderUnits.forEach(orderUnit -> {
            Long itemId = orderUnit.getItem().getId();
            if (lastItemIds.contains(itemId)) {
                orderRequestDTOs.stream().filter(dto -> dto.getItemId().equals(itemId)).findFirst().ifPresent(
                        orderRequestDTO -> {
                            if (!orderRequestDTO.getQuantity().equals(orderUnit.getQuantity())) {
                                orderUnit.setQuantity(orderRequestDTO.getQuantity());
                            }
                        }
                );
            }
        });

        Set<Long> idToRemove = oldItemIds.stream()
                .filter(oldItemId -> !lastItemIds.contains(oldItemId))
                .collect(Collectors.toSet());
        if (!idToRemove.isEmpty()) {
            orderUnitRepository.deleteByBillOrder_IdAndItem_IdIn(billOrder.getId(), idToRemove);
        }

        Set<Long> idToAdd = lastItemIds.stream()
                .filter(lastItemId -> !oldItemIds.contains(lastItemId))
                .collect(Collectors.toSet());
        if (!idToAdd.isEmpty()) {
            List<Item> items = itemRepository.findAllById(idToAdd);

            List<OrderUnit> orderUnits = orderRequestDTOs.stream()
                    .filter(dto -> idToAdd.contains(dto.getItemId()))
                    .map(orderRequestDTO -> orderConverter.toEntity(orderRequestDTO, billOrder, items)).collect(Collectors.toList());
            orderUnitRepository.saveAll(orderUnits);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public BillResponseDTO check(Long id) {
        BillOrder billOrder = getBillOrder(id);

        if (!billOrder.getOrderUnits().isEmpty()) {

            return BillResponseDTO.builder()
                    .id(billOrder.getId())
                    .totalPrice(billOrder.getOrderUnits().stream()
                            .map(d -> d.getItem().getPrice().multiply(new BigDecimal(d.getQuantity())))
                            .reduce(BigDecimal.ZERO, BigDecimal::add))
                    .orderUnits(orderUnitMapper.orderUnitsToOrderUnitDTOs(billOrder.getOrderUnits()))
                    .build();
        }

        throw new RuntimeException("Order is empty");
    }

    private BillOrder getBillOrder(Long id) {
        return billOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ITEM_WAS_NOT_FOUND_BY_ID, id)));
    }

}
