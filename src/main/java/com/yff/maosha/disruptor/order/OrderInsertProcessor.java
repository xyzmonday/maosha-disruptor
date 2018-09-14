package com.yff.maosha.disruptor.order;

import com.yff.maosha.command.CommandProcessor;
import com.yff.maosha.command.disruptor.CommandEventProducer;
import com.yff.maosha.disruptor.item.ItemAmountUpdateCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderInsertProcessor implements CommandProcessor<OrderInsertCommand> {

    private final static Logger logger = LoggerFactory.getLogger(OrderInsertProcessor.class);

    private final CommandEventProducer<OrderInsertCommand>[] commandEventProducers;

    public OrderInsertProcessor(CommandEventProducer<OrderInsertCommand>[] commandEventProducers) {
        this.commandEventProducers = commandEventProducers;
    }

    @Override
    public Class<OrderInsertCommand> commandType() {
        return OrderInsertCommand.class;
    }

    @Override
    public void process(OrderInsertCommand command) {
        logger.info("OrderInsertProcessor" + Thread.currentThread().getName());
        int index = (int) (command.getItemId() % commandEventProducers.length);
        commandEventProducers[index].publish(command);
    }
}
