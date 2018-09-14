package com.yff.maosha.memdb;

import com.yff.maosha.entity.Item;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ItemRepositoryImpl implements ItemRepository {

  private final Map<Long, Item> data = new HashMap<>(1000);

  @Override
  public void put(Item item) {
    data.put(Long.valueOf(item.getId()), item);
  }

  @Override
  public Item get(Long id) {
    return data.get(id);
  }

}
