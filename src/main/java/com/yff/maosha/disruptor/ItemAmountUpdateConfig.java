package com.yff.maosha.disruptor;

import com.lmax.disruptor.dsl.Disruptor;
import com.yff.maosha.command.CommandBuffer;
import com.yff.maosha.command.CommandDispatcher;
import com.yff.maosha.command.CommandExecutor;
import com.yff.maosha.command.disruptor.*;
import com.yff.maosha.disruptor.item.ItemAmountUpdateCommand;
import com.yff.maosha.disruptor.item.ItemAmountUpdateCommandBuffer;
import com.yff.maosha.disruptor.item.ItemAmountUpdateExecutor;
import com.yff.maosha.disruptor.item.ItemAmountUpdateProcessor;
import com.yff.maosha.mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Configuration
@EnableConfigurationProperties(value = {ItemAmountUpdateConfig.ItemAmountUpdateProperties.class})
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


    @ConfigurationProperties(prefix = "item-update.proc")
    public static class ItemAmountUpdateProperties {
        /**
         * 处理器数量
         */
        private int num;

        /**
         * 单次执行的SQL条数 (将多条SQL放到一起执行比分多次执行效率高)
         */
        private int sqlBufferSize;

        /**
         * disruptor队列长度, 值必须是2的次方
         */
        private int queueSize;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getSqlBufferSize() {
            return sqlBufferSize;
        }

        public void setSqlBufferSize(int sqlBufferSize) {
            this.sqlBufferSize = sqlBufferSize;
        }

        public int getQueueSize() {
            return queueSize;
        }

        public void setQueueSize(int queueSize) {
            this.queueSize = queueSize;
        }
    }

}
