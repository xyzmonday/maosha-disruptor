package com.yff.maosha.service.impl;

import com.yff.maosha.disruptor.item.ItemAmountUpdateCommand;
import com.yff.maosha.disruptor.order.OrderInsertCommand;
import com.yff.maosha.disruptor.request.RequestEvent;
import com.yff.maosha.entity.CommandLogDetail;
import com.yff.maosha.entity.CommandLogHeader;
import com.yff.maosha.mapper.CommandLogDetailMapper;
import com.yff.maosha.mapper.CommandLogHeaderMapper;
import com.yff.maosha.service.CommandLogService;
import com.yff.maosha.utils.CommandLogStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommandLogServiceImpl implements CommandLogService {

    private final static Logger logger = LoggerFactory.getLogger(CommandLogServiceImpl.class);

    @Autowired
    private CommandLogHeaderMapper commandLogHeaderMapper;

    @Autowired
    private CommandLogDetailMapper commandLogDetailMapper;

    @Transactional
    @Override
    public void saveCommandLog(RequestEvent event,
                               ItemAmountUpdateCommand itemAmountUpdateCommand,
                               OrderInsertCommand orderInsertCommand) {
        CommandLogHeader commandLogHeader = new CommandLogHeader();
        commandLogHeader.setRequestId(event.getRequest().getId());
        commandLogHeader.setUserId(event.getRequest().getUserId());
        commandLogHeader.setCreatedTime(new Date());
        commandLogHeaderMapper.insertSelective(commandLogHeader);

        List<CommandLogDetail> commandLogs = new ArrayList<>();
        CommandLogDetail itemAmountLog = new CommandLogDetail();
        itemAmountLog.setLogHeaderId(commandLogHeader.getId());
        itemAmountLog.setCommandId(itemAmountUpdateCommand.getId());
        itemAmountLog.setLogStatus((byte) CommandLogStatus.EMIT.getCode());
        itemAmountLog.setItemId(itemAmountUpdateCommand.getItemId());
        itemAmountLog.setAmount(itemAmountUpdateCommand.getAmount());

        CommandLogDetail orderInsertLog = new CommandLogDetail();
        orderInsertLog.setLogHeaderId(commandLogHeader.getId());
        orderInsertLog.setCommandId(orderInsertCommand.getId());
        orderInsertLog.setLogStatus((byte) CommandLogStatus.EMIT.getCode());
        orderInsertLog.setItemId(orderInsertCommand.getItemId());
        orderInsertLog.setUserId(orderInsertCommand.getUserId());

        commandLogs.add(itemAmountLog);
        commandLogs.add(orderInsertLog);
        commandLogDetailMapper.insertBatch(commandLogs);
    }

    @Override
    public void updateCommandLogStatus(String commandId, CommandLogStatus success) {
        commandLogDetailMapper.updateCommandLogStatus(commandId,success.getCode());
    }


}
