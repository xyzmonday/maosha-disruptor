package com.yff.maosha.service;

import com.yff.maosha.entity.Item;

public interface ItemService {

    /**
     * 将商品导入到内存
     * @param item
     */
    void put(Item item);

    /**
     * 从内存获取一个商品
     * @param id
     * @return
     */
    Item get(Long id);
}
