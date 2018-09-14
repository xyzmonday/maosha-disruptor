package com.yff.maosha.command.disruptor;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.yff.maosha.command.Command;

public class CommandEventProducer<T extends Command> {

    private final EventTranslatorOneArg<CommandEvent<T>, T> TRANSLATOR = new EventTranslatorOneArg<CommandEvent<T>, T>() {
        @Override
        public void translateTo(CommandEvent<T> commandEvent, long l, T t) {
            commandEvent.setCommand(t);
        }
    };

    private final RingBuffer<CommandEvent<T>> ringBuffer;

    public CommandEventProducer(RingBuffer<CommandEvent<T>> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void publish(T command) {
        this.ringBuffer.publishEvent(TRANSLATOR, command);
    }
}
