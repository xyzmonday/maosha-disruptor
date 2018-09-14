package com.yff.maosha.command;

/**
 * 命令执行器,消费到Diruptor队列之后，CommandExecutor批量执行这些命令（也就是真实的业务逻辑）
 */
public interface CommandExecutor<T extends CommandBuffer> {

    void execute(T commandBuffer);


}
