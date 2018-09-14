package com.yff.maosha.command;

/**
 * 命令处理器，链接了disruptor队列和分发器
 */
public interface CommandProcessor<T extends Command> {

    /**
     * 该处理器处理的命令类型，分发器利用该类型将需要它处理的命令分发给它处理
     * @return
     */
    Class<T> commandType();

    /**
     * 处理器命令处理
     * @param command
     */
    void process(T command);
}
