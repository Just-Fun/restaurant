package com.serzh.service.impl;

import com.serzh.domain.BillOrder;
import com.serzh.domain.Item;
import com.serzh.domain.OrderUnit;
import com.serzh.repository.BillOrderRepository;
import com.serzh.repository.ItemRepository;
import com.serzh.repository.OrderRepository;
import com.serzh.service.component.OrderConverter;
import com.serzh.service.dto.BillResponseDTO;
import com.serzh.service.dto.ItemResponseDTO;
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
public class OrderServiceImpl {

    private final OrderRepository orderRepository;
    private final BillOrderRepository billOrderRepository;
    private final ItemRepository itemRepository;
    private final OrderConverter orderConverter;
    private final OrderUnitMapper orderUnitMapper;

    public Long createOrder(List<OrderRequestDTO> orderRequestDTOs) {
        BillOrder billOrder = billOrderRepository.save(new BillOrder());

        Set<Long> itemIds = orderRequestDTOs.stream().mapToLong(OrderRequestDTO::getItemId).boxed().collect(Collectors.toSet());

        List<Item> items = itemRepository.findAllById(itemIds);

        List<OrderUnit> orderUnits = orderRequestDTOs.stream()
                .map(payload -> orderConverter.toEntity(payload, billOrder, items)).collect(Collectors.toList());

        orderRepository.saveAll(orderUnits);
        return billOrder.getId();
    }

    public ItemResponseDTO update(Long id, List<OrderRequestDTO> orderRequestDTOs) {

        return null;

    }

    @Transactional(readOnly = true)
    public BillResponseDTO check(Long id) {
        BillOrder billOrder = billOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ITEM_WAS_NOT_FOUND_BY_ID, id)));

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

}
