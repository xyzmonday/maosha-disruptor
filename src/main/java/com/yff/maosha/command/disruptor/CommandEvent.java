package com.yff.maosha.command.disruptor;

import com.yff.maosha.command.Command;

/**
 * 将comand包装成事件
 */
public class CommandEvent<T extends Command> {

    private T command;

    public T getCommand() {
        return command;
    }

    public void setCommand(T command) {
        this.command = command;
    }

    public void clearForGc() {
        this.command = null;
    }
}
