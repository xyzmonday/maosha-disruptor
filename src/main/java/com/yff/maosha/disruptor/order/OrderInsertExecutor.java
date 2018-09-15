package com.yff.maosha.disruptor.order;

import com.yff.maosha.command.CommandExecutor;
import com.yff.maosha.entity.ItemOrder;
import com.yff.maosha.mapper.ItemOrderMapper;
import com.yff.maosha.service.CommandLogService;
import com.yff.maosha.utils.CommandLogStatus;

import java.util.ArrayList;
import java.util.List;

public class OrderInsertExecutor implements CommandExecutor<OrderInsertCommandBuffer> {

    private final ItemOrderMapper itemOrderMapper;
    private final CommandLogService commandLogService;

    public OrderInsertExecutor(ItemOrderMapper itemOrderMapper,CommandLogService commandLogService) {
        this.itemOrderMapper = itemOrderMapper;
        this.commandLogService = commandLogService;
    }

    @Override
    public void execute(OrderInsertCommandBuffer commandBuffer) {
        List<ItemOrder> list = new ArrayList<>();
        List<OrderInsertCommand> commands = commandBuffer.get();
        for (OrderInsertCommand command : commands) {
            ItemOrder itemOrder = new ItemOrder();
            itemOrder.setItemId(command.getItemId());
            itemOrder.setUserId(command.getUserId());
            list.add(itemOrder);
        }
        if(list.size() > 0) {
            //执行订单生成
            itemOrderMapper.batchInsert(list);
            for (OrderInsertCommand command : commands) {
                commandLogService.updateCommandLogStatus(command.getId(), CommandLogStatus.SUCCESS);
            }
        }
    }
}
