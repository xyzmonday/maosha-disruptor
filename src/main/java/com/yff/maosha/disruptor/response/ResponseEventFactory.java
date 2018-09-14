package com.yff.maosha.disruptor.response;

import com.lmax.disruptor.EventFactory;

public class ResponseEventFactory implements EventFactory<ResponseEvent> {
    @Override
    public ResponseEvent newInstance() {
        return new ResponseEvent();
    }
}
