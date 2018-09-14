package com.yff.maosha.service.impl;

import com.yff.maosha.entity.Item;
import com.yff.maosha.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {
    private final Map<Long, Item> data = new HashMap<>(1000);

    @Override
    public void put(Item item) {
        data.put(item.getId(), item);
    }

    @Override
    public Item get(Long id) {
        return data.get(id);
    }
}
