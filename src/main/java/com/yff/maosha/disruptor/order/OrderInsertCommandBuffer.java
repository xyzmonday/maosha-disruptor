package com.yff.maosha.disruptor.order;

import com.yff.maosha.command.CommandBuffer;
import com.yff.maosha.command.CommandBufferOverflowException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 订单生成是每一个用户每一个秒杀请求一个记录
 */
public class OrderInsertCommandBuffer implements CommandBuffer<OrderInsertCommand> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderInsertCommandBuffer.class);

    private final List<OrderInsertCommand> commandList;

    private final int capacity;

    public OrderInsertCommandBuffer(int capacity) {
        this.capacity = capacity;
        this.commandList = new ArrayList<>(capacity);
    }

    @Override
    public boolean hasRemaining() {
        return commandList.size() < this.capacity;
    }


    @Override
    public void put(OrderInsertCommand command) {
        if (!hasRemaining()) {
            throw new CommandBufferOverflowException();
        }
        this.commandList.add(command);
    }

    @Override
    public void clear() {
        commandList.clear();
    }

    @Override
    public List<OrderInsertCommand> get() {
        return new ArrayList<>(commandList);
    }
}
