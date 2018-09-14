package com.yff.maosha.command.disruptor;

import com.lmax.disruptor.EventFactory;
import com.yff.maosha.command.Command;

public class CommandEventFactory<T extends Command> implements EventFactory<CommandEvent<T>> {

    @Override
    public CommandEvent<T> newInstance() {
        return new CommandEvent<>();
    }
}
