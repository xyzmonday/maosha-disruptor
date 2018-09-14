package com.yff.maosha.mapper;

import com.yff.maosha.MaoshaApplication;
import com.yff.maosha.entity.ItemOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MaoshaApplication.class)
@MapperScan("com.yff.maosha.mapper")
public class ItemOrderMapperTest {

    @Autowired
    private ItemOrderMapper itemOrderMapper;


    @Test
    public void itemOrderUpdateTest() {

        List<ItemOrder> itemOrders = new ArrayList<>();
        ItemOrder itemOrder1 = new ItemOrder();
        itemOrder1.setItemId(1L);
        itemOrder1.setUserId("yuanfengfan");

        ItemOrder itemOrder2 = new ItemOrder();
        itemOrder2.setItemId(2L);
        itemOrder2.setUserId("yuanfengfan");

        itemOrders.add(itemOrder1);
        itemOrders.add(itemOrder2);

        itemOrderMapper.batchInsert(itemOrders);

    }

}
