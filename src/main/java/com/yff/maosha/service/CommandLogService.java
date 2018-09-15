package com.yff.maosha.service;

import com.yff.maosha.command.Command;
import com.yff.maosha.disruptor.item.ItemAmountUpdateCommand;
import com.yff.maosha.disruptor.order.OrderInsertCommand;
import com.yff.maosha.disruptor.request.RequestEvent;
import com.yff.maosha.utils.CommandLogStatus;

import java.util.List;

/**
 * 数据库Command日志服务
 */
public interface CommandLogService {

    /**
     * 保存数据库命令日志
     * @param event
     * @param itemAmountUpdateCommand
     * @param orderInsertCommand
     */
    void saveCommandLog(RequestEvent event,
                        ItemAmountUpdateCommand itemAmountUpdateCommand,
                        OrderInsertCommand orderInsertCommand);

    void updateCommandLogStatus(String commandId, CommandLogStatus success);
}
