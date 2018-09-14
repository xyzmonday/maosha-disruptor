package com.yff.maosha.disruptor.request;

import com.lmax.disruptor.EventFactory;

/**
 * 请求事件工厂
 */
public class RequestEventFactory implements EventFactory<RequestEvent> {

    @Override
    public RequestEvent newInstance() {
        return new RequestEvent();
    }
}
