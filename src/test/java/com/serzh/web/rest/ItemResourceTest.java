package com.serzh.web.rest;

import com.serzh.domain.Item;
import com.serzh.domain.Type;
import com.serzh.service.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ItemResourceTest {

    private static final String ITEM_NAME = "Hawaiian Pizza";
    private static final String ITEM_DESCRIPTION = "All-time favourite toppings, Hawaiian pizza in Tropical Hawaii style.";
    private static final String ITEM_IMAGE = "https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu1.jpg";
    private static final String ITEM_TYPE_ITALIAN = "Italian";
    private static final String ITEM_TYPE_HAM = "Ham";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Test
    public void findAllItemsTest() throws Exception {
        Item item = Item.builder()
                .id(1L)
                .name(ITEM_NAME)
                .description(ITEM_DESCRIPTION)
                .image(ITEM_IMAGE)
                .price(new BigDecimal("300.25"))
                .types(new HashSet<>(Arrays.asList(Type.builder().name(ITEM_TYPE_ITALIAN).build(), Type.builder().name(ITEM_TYPE_HAM).build())))
                .build();

        Page<Item> page = new PageImpl<>(Collections.singletonList(item), PageRequest.of(0, 1), 1L);

        Pageable pageOf = PageRequest.of(0, 10);
        when(itemService.findAll("some", pageOf)).thenReturn(page);

        mockMvc.perform(get("/api/v1/items?keyword=some&page=0&size=10"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[*].id").value(hasItem(1)))
                .andExpect(jsonPath("$.[*].name").value(hasItem(ITEM_NAME)))
                .andExpect(jsonPath("$.[*].description").value(hasItem(ITEM_DESCRIPTION)))
                .andExpect(jsonPath("$.[*].image").value(hasItem(ITEM_IMAGE)))
                .andExpect(jsonPath("$.[*].price").value(hasItem(300.25)))
                .andExpect(jsonPath("$.[*].types[*].name", containsInAnyOrder(ITEM_TYPE_ITALIAN, ITEM_TYPE_HAM)));

        verify(itemService, only()).findAll("some", pageOf);
    }

}