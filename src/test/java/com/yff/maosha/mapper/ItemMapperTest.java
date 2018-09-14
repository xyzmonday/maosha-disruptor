package com.yff.maosha.mapper;

import com.yff.maosha.MaoshaApplication;
import com.yff.maosha.entity.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MaoshaApplication.class)
@MapperScan("com.yff.maosha.mapper")
public class ItemMapperTest {

    private final static Logger logger = LoggerFactory.getLogger(ItemMapperTest.class);

    @Autowired
    private ItemMapper itemMapper;

    @Test
    public void itemMapperQueryTest() {
        List<Item> items = itemMapper.selectByExample(null);
        System.out.println(items);
    }

    @Test
    public void itemMapperInsert() {
        List<Item> items = itemMapper.selectByExample(null);
        for (Item item : items) {
            item.setAmount(10);
        }
        itemMapper.batchUpdateAmount(items);
    }
}

