package com.yff.maosha.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 命令分发器，分发的原则的是相同的业务command分发到同一个队列里面
 */
public class DefaultCommandDispatcher implements CommandDispatcher {

    private final static Logger logger = LoggerFactory.getLogger(DefaultCommandDispatcher.class);

    private final Map<Class<Command>, CommandProcessor<Command>> map = new HashMap<>();

    @Override
    public void dispatch(Command command) {
        CommandProcessor<Command> commandProcessor = map.get(command.getClass());
        if (commandProcessor == null) {
            logger.warn("commandProcessor for command : {} is not registered", command.getClass().getSimpleName());
        }
        commandProcessor.process(command);
    }

    @Override
    public void registerCommandProcessor(CommandProcessor processor) {
        logger.info("Register TO DefaultCommandDispatcher: [{}] for [{}]", processor.getClass().getName(), processor.commandType().getName());
        map.put(processor.commandType(), processor);
    }

}
