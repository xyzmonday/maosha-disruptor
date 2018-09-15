package com.yff.maosha.disruptor.request;

import com.lmax.disruptor.EventHandler;
import com.yff.maosha.command.Command;
import com.yff.maosha.command.CommandDispatcher;
import com.yff.maosha.entity.CommandLogHeader;
import com.yff.maosha.service.CommandLogService;
import com.yff.maosha.utils.CommandLogStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

public class RequestEventDbHandler implements EventHandler<RequestEvent> {

    private static final Logger logger = LoggerFactory.getLogger(RequestEventJmsHandler.class);

    /**
     * sql命令分发器
     */
    private final CommandDispatcher commandDispatcher;


    public RequestEventDbHandler(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    @Override
    public void onEvent(RequestEvent event, long sequence, boolean endOfBatch) throws Exception {
        List<Command> commandList = event.getCommandList();
        if (CollectionUtils.isEmpty(commandList)) {
            return;
        }
        for (Command command : commandList) {
            this.commandDispatcher.dispatch(command);
        }
    }
}
