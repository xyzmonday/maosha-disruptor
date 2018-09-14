package com.yff.maosha.disruptor.request;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.yff.maosha.model.RequestDto;

/**
 * 请求事件生产着
 */
public class RequestEventProducer {

    /**
     * 事件转化器 request -> RequestEvent
     */
    private final EventTranslatorOneArg<RequestEvent, RequestDto> TRANSLATOR = (requestEvent, l, requestDto) -> requestEvent.setRequest(requestDto);

    private final RingBuffer<RequestEvent> ringBuffer;

    public RequestEventProducer(RingBuffer<RequestEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void publish(RequestDto request) {
        this.ringBuffer.publishEvent(TRANSLATOR,request);
    }

}
