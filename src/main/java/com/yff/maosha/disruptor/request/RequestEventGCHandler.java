package com.yff.maosha.disruptor.request;

import com.lmax.disruptor.EventHandler;

/**
 * 清除请求事件的实体数据
 */
public class RequestEventGCHandler implements EventHandler<RequestEvent> {
    @Override
    public void onEvent(RequestEvent event, long l, boolean b) throws Exception {
        event.clearForGC();
    }
}
