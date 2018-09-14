package com.yff.maosha.disruptor.response;


import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;

import javax.jms.Topic;

/**
 * 消费将disruptor的响应，将其发送到ActiveMq中，供客户端查询
 */
public class ResponseEventHandler implements EventHandler<ResponseEvent> {

    private final static Logger logger = LoggerFactory.getLogger(ResponseEventProducer.class);

    private final JmsMessagingTemplate jmsMessagingTemplate;

    private final Topic topic;

    public ResponseEventHandler(JmsMessagingTemplate jmsMessagingTemplate, Topic topic) {
        this.jmsMessagingTemplate = jmsMessagingTemplate;
        this.topic = topic;
    }


    @Override
    public void onEvent(ResponseEvent responseEvent, long l, boolean b) throws Exception {
        logger.info("消费响应队列，将响应发送到客户端");
        jmsMessagingTemplate.convertAndSend(topic, responseEvent.getResponse());
        responseEvent.setResponse(null);
    }
}
