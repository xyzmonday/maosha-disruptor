package com.yff.maosha.activemq;

import com.yff.maosha.disruptor.request.RequestEventProducer;
import com.yff.maosha.model.RequestDto;
import com.yff.maosha.model.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;

@Component
public class JmsConsumer {

    private final static Logger logger = LoggerFactory.getLogger(JmsConsumer.class);

    @Autowired
    private RequestEventProducer requestEventProducer;

    /**
     * 收到秒杀请求消息
     */
    @JmsListener(destination = "miaosha.queue")
    public void miaoshaRequest(RequestDto request) throws JMSException {
        logger.info("客户端收到秒杀的请求");
        requestEventProducer.publish(request);
    }

    @JmsListener(destination = "miaosha.topic")
    public void miaoshaResponse(ResponseDto response) {
        logger.info("收到服务端秒杀结果");
    }
}
