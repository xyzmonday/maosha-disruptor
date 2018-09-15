package com.yff.maosha.disruptor.item;

import com.yff.maosha.command.CommandExecutor;
import com.yff.maosha.entity.Item;
import com.yff.maosha.mapper.ItemMapper;
import com.yff.maosha.service.CommandLogService;
import com.yff.maosha.utils.CommandLogStatus;

import java.util.ArrayList;
import java.util.List;

public class ItemAmountUpdateExecutor implements CommandExecutor<ItemAmountUpdateCommandBuffer> {

    private final ItemMapper itemMapper;
    private final CommandLogService commandLogService;

    public ItemAmountUpdateExecutor(ItemMapper itemMapper,CommandLogService commandLogService) {
        this.itemMapper = itemMapper;
        this.commandLogService = commandLogService;
    }

    @Override
    public void execute(ItemAmountUpdateCommandBuffer commandBuffer) {
        List<Item> list = new ArrayList<>();
        List<ItemAmountUpdateCommand> commands = commandBuffer.get();
        for (ItemAmountUpdateCommand command : commands) {
            Item  item = new Item();
            item.setId(command.getItemId());
            item.setAmount(command.getAmount());
            list.add(item);
        }
        if(list.size() > 0) {
            itemMapper.batchUpdateAmount(list);
            for (ItemAmountUpdateCommand command : commands) {
                commandLogService.updateCommandLogStatus(command.getId(), CommandLogStatus.SUCCESS);
            }
        }
    }
}
