package com.yff.maosha.disruptor.response;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.yff.maosha.model.ResponseDto;

/**
 * 该producer将请求的响应写入Disruptor队列
 */
public class ResponseEventProducer {

    //response -> responseEvent
    private final EventTranslatorOneArg<ResponseEvent, ResponseDto> translatorOneArg = new EventTranslatorOneArg<ResponseEvent, ResponseDto>() {
        @Override
        public void translateTo(ResponseEvent responseEvent, long l, ResponseDto responseDto) {
            responseEvent.setResponse(responseDto);
        }
    };


    private final RingBuffer<ResponseEvent> ringBuffer;

    public ResponseEventProducer(RingBuffer<ResponseEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void publish(ResponseDto response) {
        this.ringBuffer.publishEvent(translatorOneArg, response);
    }

}
