package com.yff.maosha.disruptor;

import com.lmax.disruptor.dsl.Disruptor;
import com.yff.maosha.command.CommandBuffer;
import com.yff.maosha.command.CommandDispatcher;
import com.yff.maosha.command.CommandExecutor;
import com.yff.maosha.command.disruptor.*;
import com.yff.maosha.disruptor.order.OrderInsertCommand;
import com.yff.maosha.disruptor.order.OrderInsertCommandBuffer;
import com.yff.maosha.disruptor.order.OrderInsertExecutor;
import com.yff.maosha.disruptor.order.OrderInsertProcessor;
import com.yff.maosha.mapper.ItemOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Configuration
@EnableConfigurationProperties(value = {OrderInsertConfig.OrderInsertProperties.class})
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
            disruptor.start();
        }

        OrderInsertProcessor orderInsertProcessor = new OrderInsertProcessor(commandEventProducers);
        commandDispatcher.registerCommandProcessor(orderInsertProcessor);
        return orderInsertProcessor;
    }

    @ConfigurationProperties(prefix = "order-insert.proc")
    public  static class OrderInsertProperties {

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
