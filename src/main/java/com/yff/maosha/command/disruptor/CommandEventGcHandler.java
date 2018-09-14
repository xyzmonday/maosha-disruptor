package com.yff.maosha.command.disruptor;


import com.lmax.disruptor.EventHandler;
import com.yff.maosha.command.Command;

/**
 * DbCommandEvent的GC处理器
 */
public class CommandEventGcHandler<T extends Command> implements EventHandler<CommandEvent<T>> {

    @Override
    public void onEvent(CommandEvent<T> event, long sequence, boolean endOfBatch) throws Exception {
        event.clearForGc();
    }

}
