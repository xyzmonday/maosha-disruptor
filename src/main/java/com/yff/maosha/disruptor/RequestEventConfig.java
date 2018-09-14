package com.yff.maosha.disruptor;

import com.lmax.disruptor.dsl.Disruptor;
import com.yff.maosha.command.CommandDispatcher;
import com.yff.maosha.command.DefaultCommandDispatcher;
import com.yff.maosha.disruptor.request.*;
import com.yff.maosha.disruptor.response.ResponseEventProducer;
import com.yff.maosha.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

/**
 * 配置请求事件生产者
 */
@Configuration
public class RequestEventConfig {

    /**
     * 秒杀请求队列大小
     */
    @Value("${request.ring-buffer-size}")
    private int requestQueueSize;

    @Autowired
    private ResponseEventProducer responseEventProducer;

    @Autowired
    private ItemService itemService;

   @Bean
   public CommandDispatcher commandDispatcher() {
       return new DefaultCommandDispatcher();
   }

    @Bean
    public RequestEventProducer requestEventProducer() {
        RequestEventFactory requestEventFactory = new RequestEventFactory();
        //这里默认的生产这是多线程
        Disruptor<RequestEvent> disruptor = new Disruptor<>(requestEventFactory, requestQueueSize,
                Executors.defaultThreadFactory());

        //请求事件生产者
        RequestEventProducer requestEventProducer = new RequestEventProducer(disruptor.getRingBuffer());

        //业务消费者
        RequestEventBizHandler requestEventBizHandler = new RequestEventBizHandler(itemService);

        //将业务消费的数据库命令分发到指定的业务处理器
        RequestEventDbHandler requestEventDbHandler = new RequestEventDbHandler(commandDispatcher());

        //将业务消费的响应发送给客户端
        RequestEventJmsHandler requestEventJmsHandler = new RequestEventJmsHandler(responseEventProducer);

        //gc
        RequestEventGCHandler requestEventGCHandler = new RequestEventGCHandler();

        //异常消费者
        RequestEventExceptionHandler requestEventExceptionHandler = new RequestEventExceptionHandler();

        disruptor.handleEventsWith(requestEventBizHandler)
                .then(requestEventDbHandler, requestEventJmsHandler)
                .then(requestEventGCHandler);

        disruptor.setDefaultExceptionHandler(requestEventExceptionHandler);

        disruptor.start();
        return requestEventProducer;
    }
}
