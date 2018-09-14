package com.yff.maosha.disruptor.item;

import com.yff.maosha.command.CommandBuffer;
import com.yff.maosha.command.CommandBufferOverflowException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 由于更新商品库存只需要执行最后一个sql,所以相同的商品分组即可
 */
public class ItemAmountUpdateCommandBuffer implements CommandBuffer<ItemAmountUpdateCommand> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemAmountUpdateCommandBuffer.class);

    private final Map<Long, ItemAmountUpdateCommand> commandBuffer;
    private final int capacity;

    public ItemAmountUpdateCommandBuffer(int capacity) {
        this.capacity = capacity;
        this.commandBuffer = new HashMap<>(capacity);
    }

    @Override
    public void put(ItemAmountUpdateCommand command) {
        Long key = command.getItemId();
        if (!hasRemaining() && commandBuffer.get(key) == null) {
            throw new CommandBufferOverflowException();
        }
        ItemAmountUpdateCommand prevValue = this.commandBuffer.put(key, command);
        if (prevValue != null) {
            LOGGER.info("Optimized", command);
        }
        LOGGER.info("Put", command);
    }

    @Override
    public boolean hasRemaining() {
        return commandBuffer.size() > this.capacity;
    }

    @Override
    public List<ItemAmountUpdateCommand> get() {
        return new ArrayList<>(commandBuffer.values());
    }

    @Override
    public void clear() {
        this.commandBuffer.clear();
    }
}
