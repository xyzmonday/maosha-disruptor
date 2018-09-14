package com.yff.maosha.disruptor;

import com.lmax.disruptor.dsl.Disruptor;
import com.yff.maosha.disruptor.response.ResponseEventHandler;
import com.yff.maosha.disruptor.response.ResponseEvent;
import com.yff.maosha.disruptor.response.ResponseEventFactory;
import com.yff.maosha.disruptor.response.ResponseEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsMessagingTemplate;

import javax.jms.Topic;
import java.util.concurrent.Executors;

@Configuration
public class ResponseEventConfig {


    @Value("${reponse.ring-buffer-size}")
    private int responseQueueSize;

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Topic topic;

    @Bean
    public ResponseEventProducer responseEventProducer() {
        ResponseEventFactory responseEventFactory = new ResponseEventFactory();
        Disruptor<ResponseEvent> disruptor = new Disruptor<>(responseEventFactory, responseQueueSize, Executors.defaultThreadFactory());
        ResponseEventProducer responseEventProducer = new ResponseEventProducer(disruptor.getRingBuffer());
        disruptor.handleEventsWith(new ResponseEventHandler(jmsMessagingTemplate,topic));
        return responseEventProducer;
    }

}
