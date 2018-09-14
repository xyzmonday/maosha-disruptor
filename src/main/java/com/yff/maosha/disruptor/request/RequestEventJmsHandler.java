package com.yff.maosha.disruptor.request;

import com.lmax.disruptor.EventHandler;
import com.yff.maosha.disruptor.response.ResponseEventProducer;
import com.yff.maosha.model.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 将本次秒杀请求的处理结果发送给diruptor队列。该队列就是生产本次秒杀请求的响应结果
 */
public class RequestEventJmsHandler implements EventHandler<RequestEvent> {

    private static final Logger logger = LoggerFactory.getLogger(RequestEventJmsHandler.class);

    private final ResponseEventProducer responseEventProducer;

    public RequestEventJmsHandler(ResponseEventProducer responseEventProducer) {
        this.responseEventProducer = responseEventProducer;
    }

    @Override
    public void onEvent(RequestEvent requestEvent, long l, boolean b) throws Exception {
        logger.info("JmsHandler" + Thread.currentThread().getName());
        ResponseDto response = requestEvent.getResponse();
        if (response == null) {
            return;
        }
        logger.debug("Send Response for Request {}. Id: {}", response.getRequestId(), response.getId());
        //将响应结果发送到Disruptor队列
        this.responseEventProducer.publish(response);
    }
}
