package com.yff.maosha.disruptor;

import com.lmax.disruptor.dsl.Disruptor;
import com.yff.maosha.command.CommandBuffer;
import com.yff.maosha.command.CommandDispatcher;
import com.yff.maosha.command.CommandExecutor;
import com.yff.maosha.command.disruptor.*;
import com.yff.maosha.config.ItemAmountUpdateProperties;
import com.yff.maosha.disruptor.item.ItemAmountUpdateCommand;
import com.yff.maosha.disruptor.item.ItemAmountUpdateCommandBuffer;
import com.yff.maosha.disruptor.item.ItemAmountUpdateExecutor;
import com.yff.maosha.disruptor.item.ItemAmountUpdateProcessor;
import com.yff.maosha.mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Configuration
@EnableConfigurationProperties(value = {ItemAmountUpdateProperties.class})
public class ItemAmountUpdateConfig {


    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private CommandDispatcher commandDispatcher;

    @Autowired
    private ItemAmountUpdateProperties itemAmountUpdateProperties;

    @Bean
    public ItemAmountUpdateProcessor itemAmountUpdateProcessor() {
        //按照商品的分组（这里分组的规则就是取模），同一组的商品使用同一个处理器
        int num = itemAmountUpdateProperties.getNum();
        CommandEventProducer<ItemAmountUpdateCommand>[] commandEventProducers = new CommandEventProducer[num];
        for (int i = 0; i < num; i++) {
            Disruptor<CommandEvent<ItemAmountUpdateCommand>> disruptor = new Disruptor<>(
                    new CommandEventFactory(),
                    itemAmountUpdateProperties.getQueueSize(),
                    Executors.defaultThreadFactory());

            CommandBuffer<ItemAmountUpdateCommand> commandBuffer =
                    new ItemAmountUpdateCommandBuffer(itemAmountUpdateProperties.getSqlBufferSize());

            CommandExecutor<ItemAmountUpdateCommandBuffer> commandExecutor =
                    new ItemAmountUpdateExecutor(itemMapper);

            CommandEventDbHandler<ItemAmountUpdateCommand> commandEventDbHandler =
                    new CommandEventDbHandler<>(commandBuffer, commandExecutor);

            disruptor.handleEventsWith(commandEventDbHandler).then(new CommandEventGcHandler());
            disruptor.setDefaultExceptionHandler(new CommandEventExceptionHandler());

            CommandEventProducer<ItemAmountUpdateCommand> commandEventProducer =
                    new CommandEventProducer<>(disruptor.getRingBuffer());
            commandEventProducers[i] = commandEventProducer;
            disruptor.start();
        }
        ItemAmountUpdateProcessor itemAmountUpdateProcessor = new ItemAmountUpdateProcessor(commandEventProducers);
        commandDispatcher.registerCommandProcessor(itemAmountUpdateProcessor);
        return itemAmountUpdateProcessor;
    }




}
