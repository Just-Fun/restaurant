package com.serzh.service.component;

import com.serzh.domain.BillOrder;
import com.serzh.domain.Item;
import com.serzh.domain.OrderUnit;
import com.serzh.service.dto.OrderRequestDTO;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.serzh.service.util.Constants.ITEM_WAS_NOT_FOUND_BY_ID;
import static com.serzh.service.util.Constants.ZONE_ID_KIEV;

@Component
public class OrderConverter {

    public OrderUnit toEntity(OrderRequestDTO payload, BillOrder billOrder, List<Item> items) {
        return OrderUnit.builder()
                .item(items.stream().filter(i -> Objects.equals(i.getId(), payload.getItemId())).findFirst()
                        .orElseThrow(() -> new EntityNotFoundException(String.format(ITEM_WAS_NOT_FOUND_BY_ID, payload.getItemId()))))
                .quantity(payload.getQuantity())
                .orderedTime(LocalDateTime.now(ZONE_ID_KIEV).withNano(0))
                .billOrder(billOrder)
                .build();
    }

}
