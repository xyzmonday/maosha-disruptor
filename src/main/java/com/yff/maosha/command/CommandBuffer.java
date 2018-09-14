package com.yff.maosha.command;

import java.util.List;

/**
 * 命令缓存容器，批量执行
 */
public interface CommandBuffer<T extends Command> {

    /**
     * 向容器中增加一个命令
     * @param command
     */
    void put(T command);

    /**
     * 是否还有剩余容量可用
     * @return
     */
    boolean hasRemaining();

    /**
     * 返回容器所有的命令
     * @return
     */
    List<T> get();

    /**
     * 清空缓存
     */
    void clear();
}
