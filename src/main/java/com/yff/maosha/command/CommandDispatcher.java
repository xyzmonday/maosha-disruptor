package com.yff.maosha.command;

public interface CommandDispatcher {

    /**
     * 分发command
     * @param command
     */
    void dispatch(Command command);

    /**
     * 注册command处理器
     * @param processor
     */
    void registerCommandProcessor(CommandProcessor processor);
}
