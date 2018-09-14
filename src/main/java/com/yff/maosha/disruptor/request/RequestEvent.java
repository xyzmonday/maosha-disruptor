package com.yff.maosha.disruptor.request;

import com.yff.maosha.command.Command;
import com.yff.maosha.model.RequestDto;
import com.yff.maosha.model.ResponseDto;

import java.util.ArrayList;
import java.util.List;

/**
 * 请求事件
 */
public class RequestEvent {
    /**
     * 请求体
     */
    private RequestDto request;
    /**
     * 响应体
     */
    private ResponseDto response;

    private final List<Command> commandList = new ArrayList<>();

    public RequestDto getRequest() {
        return request;
    }

    public void setRequest(RequestDto request) {
        this.request = request;
    }

    public ResponseDto getResponse() {
        return response;
    }

    public void setResponse(ResponseDto response) {
        this.response = response;
    }

    public void addCommand(Command command) {
        this.commandList.add(command);
    }

    public List<Command> getCommandList() {
        return this.commandList;
    }

    public void clearForGC() {
        this.request = null;
        this.response = null;
        this.commandList.clear();
    }
}
