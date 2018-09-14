package com.yff.maosha.disruptor.item;

import com.yff.maosha.command.CommandExecutor;
import com.yff.maosha.entity.Item;
import com.yff.maosha.mapper.ItemMapper;

import java.util.ArrayList;
import java.util.List;

public class ItemAmountUpdateExecutor implements CommandExecutor<ItemAmountUpdateCommandBuffer> {

    private final ItemMapper itemMapper;

    public ItemAmountUpdateExecutor(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    @Override
    public void execute(ItemAmountUpdateCommandBuffer commandBuffer) {
        List<Item> list = new ArrayList<>();
        for (ItemAmountUpdateCommand command : commandBuffer.get()) {
            Item  item = new Item();
            item.setId(command.getItemId());
            item.setAmount(command.getAmount());
            list.add(item);
        }
        if(list.size() > 0) {
            itemMapper.batchUpdateAmount(list);
        }
    }
}
