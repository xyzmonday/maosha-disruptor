package com.yff.maosha.command.disruptor;

import com.lmax.disruptor.EventHandler;
import com.yff.maosha.command.Command;
import com.yff.maosha.command.CommandBuffer;
import com.yff.maosha.command.CommandExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用数据处理CommandEvent事件
 */
public class CommandEventDbHandler<T extends Command> implements EventHandler<CommandEvent<T>> {

    private final static Logger logger = LoggerFactory.getLogger(CommandEventDbHandler.class);
    /**
     * 命令缓存
     */
    private final CommandBuffer commandBuffer;
    /**
     * 命令执行器
     */
    private final CommandExecutor commandExecutor;

    public CommandEventDbHandler(CommandBuffer commandBuffer, CommandExecutor commandExecutor) {
        this.commandBuffer = commandBuffer;
        this.commandExecutor = commandExecutor;
    }

    @Override
    public void onEvent(CommandEvent<T> commandEvent, long sequence, boolean endOfBatch) throws Exception {
        logger.info("CommandEventDbHandler ;endOfBatch:{}",endOfBatch);
        if(!this.commandBuffer.hasRemaining()) {
            flushBuffer();
        }
        this.commandBuffer.put(commandEvent.getCommand());
        if(endOfBatch) {
            flushBuffer();
        }
    }

    private void flushBuffer() {
        this.commandExecutor.execute(this.commandBuffer);
        this.commandBuffer.clear();
    }

}
