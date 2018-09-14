package com.yff.maosha.disruptor.item;

import com.yff.maosha.command.CommandProcessor;
import com.yff.maosha.command.disruptor.CommandEventProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemAmountUpdateProcessor implements CommandProcessor<ItemAmountUpdateCommand> {

    private final static Logger logger = LoggerFactory.getLogger(ItemAmountUpdateProcessor.class);

    /**
     * 将Command包装成CommandEvent发送到Disruptor队列
     */
    private final CommandEventProducer<ItemAmountUpdateCommand>[] commandEventProducers;

    public ItemAmountUpdateProcessor(CommandEventProducer<ItemAmountUpdateCommand>[] commandEventProducers) {
        this.commandEventProducers = commandEventProducers;
    }

    @Override
    public Class<ItemAmountUpdateCommand> commandType() {
        return ItemAmountUpdateCommand.class;
    }

    @Override
    public void process(ItemAmountUpdateCommand command) {
        logger.info("ItemAmountUpdateProcessor:" + Thread.currentThread().getName());
        int index = (int) (command.getItemId() % commandEventProducers.length);
        //1,5;2,6同组
        commandEventProducers[index].publish(command);
    }
}
