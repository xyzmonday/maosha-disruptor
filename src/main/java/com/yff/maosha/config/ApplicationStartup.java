package com.yff.maosha.config;

import com.yff.maosha.entity.Item;
import com.yff.maosha.mapper.ItemMapper;
import com.yff.maosha.memdb.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;


/**
 * 容器启动完毕
 */
@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    private final static Logger logger = LoggerFactory.getLogger(ApplicationStartup.class);

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<Item> items = itemMapper.selectByExample(null);
        if(!CollectionUtils.isEmpty(items)) {
            items.stream().forEach(e -> itemRepository.put(e));
        }
    }
}
