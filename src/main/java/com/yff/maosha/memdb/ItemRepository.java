package com.yff.maosha.memdb;

import com.yff.maosha.entity.Item;

/**
 * 商品内存数据库
 */
public interface ItemRepository {

  void put(Item item);

  Item get(Long id);

}
