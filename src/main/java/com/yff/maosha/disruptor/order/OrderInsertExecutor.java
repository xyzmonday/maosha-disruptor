package com.yff.maosha.disruptor.order;

import com.yff.maosha.command.CommandExecutor;
import com.yff.maosha.entity.ItemOrder;
import com.yff.maosha.mapper.ItemOrderMapper;

import java.util.ArrayList;
import java.util.List;

public class OrderInsertExecutor implements CommandExecutor<OrderInsertCommandBuffer> {

    private final ItemOrderMapper itemOrderMapper;

    public OrderInsertExecutor(ItemOrderMapper itemOrderMapper) {
        this.itemOrderMapper = itemOrderMapper;
    }

    @Override
    public void execute(OrderInsertCommandBuffer commandBuffer) {
        List<ItemOrder> list = new ArrayList<>();
        for (OrderInsertCommand command : commandBuffer.get()) {
            ItemOrder itemOrder = new ItemOrder();
            itemOrder.setItemId(command.getItemId());
            itemOrder.setUserId(command.getUserId());
            list.add(itemOrder);
        }
        if(list.size() > 0) {
            itemOrderMapper.batchInsert(list);
        }
    }
}
