package com.yff.maosha.disruptor;

import com.lmax.disruptor.dsl.Disruptor;
import com.yff.maosha.command.CommandBuffer;
import com.yff.maosha.command.CommandDispatcher;
import com.yff.maosha.command.CommandExecutor;
import com.yff.maosha.command.disruptor.*;
import com.yff.maosha.config.OrderInsertProperties;
import com.yff.maosha.disruptor.order.OrderInsertCommand;
import com.yff.maosha.disruptor.order.OrderInsertCommandBuffer;
import com.yff.maosha.disruptor.order.OrderInsertExecutor;
import com.yff.maosha.disruptor.order.OrderInsertProcessor;
import com.yff.maosha.mapper.ItemOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Configuration
@EnableConfigurationProperties(value = {OrderInsertProperties.class})
public class OrderInsertConfig {


    @Autowired
    private ItemOrderMapper itemOrderMapper;

    @Autowired
    private CommandDispatcher commandDispatcher;

    @Autowired
    private OrderInsertProperties orderInsertProperties;

    @Bean
    public OrderInsertProcessor orderInsertProcessor() {
        int num = orderInsertProperties.getNum();
        CommandEventProducer<OrderInsertCommand>[] commandEventProducers = new CommandEventProducer[num];
        for (int i = 0; i < num; i++) {

            Disruptor<CommandEvent<OrderInsertCommand>> disruptor = new Disruptor<>(
                    new CommandEventFactory(),
                    orderInsertProperties.getQueueSize(),
                    Executors.defaultThreadFactory());

            CommandBuffer<OrderInsertCommand> commandBuffer = new OrderInsertCommandBuffer(orderInsertProperties.getSqlBufferSize());
            CommandExecutor<OrderInsertCommandBuffer> commandExecutor = new OrderInsertExecutor(itemOrderMapper);
            disruptor.handleEventsWith(new CommandEventDbHandler(commandBuffer, commandExecutor))
                    .then(new CommandEventGcHandler());

            disruptor.setDefaultExceptionHandler(new CommandEventExceptionHandler<>());

            CommandEventProducer<OrderInsertCommand> commandEventProducer =
                    new CommandEventProducer<>(disruptor.getRingBuffer());
            commandEventProducers[i] = commandEventProducer;
        }

        OrderInsertProcessor orderInsertProcessor = new OrderInsertProcessor(commandEventProducers);
        commandDispatcher.registerCommandProcessor(orderInsertProcessor);
        return orderInsertProcessor;
    }



}
